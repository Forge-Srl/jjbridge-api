package jjbridge.api.inspector;

import jjbridge.api.runtime.JSRuntime;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JSBaseInspectorTest
{
    private static class JSBaseInspectorForTest<R extends JSRuntime> extends JSBaseInspector<R>
    {
        Connection connection;
        JSBaseInspectorForTest(int port)
        {
            super(port);
        }

        @Override
        protected MessageHandler newMessageHandler(Connection connection, JSRuntime runtime)
        {
            this.connection = connection;
            return null;
        }
    }

    private static abstract class ClientForTest extends WebSocketClient
    {
        public ClientForTest(InetSocketAddress address) throws URISyntaxException
        {
            super(new URI("ws://" + address.getHostString() + ":" + address.getPort()));
        }

        @Override
        public void onOpen(ServerHandshake serverHandshake) { }
        @Override
        public void onMessage(String s) { }
        @Override
        public void onClose(int i, String s, boolean b) { }
        @Override
        public void onError(Exception e) { }
    }

    private static final int port = 9369;
    @Mock
    private JSRuntime runtime;
    @Spy
    private JSBaseInspector<JSRuntime> inspector = new JSBaseInspectorForTest<>(port);

    @Test()
    public final void onError()
    {
        assertThrows(RuntimeException.class, () -> inspector.onError(null, null));
    }

    @Test
    public final void setRuntime()
    {
        inspector.setRuntime(runtime);
        verify(runtime).onInspectorAttached(inspector);
    }

    @Test
    public final void attach_firstTime()
    {
        doNothing().when(inspector).setRuntime(runtime);
        doNothing().when(inspector).startBlocking();

        inspector.attach(runtime);
        verify(inspector).setRuntime(runtime);
        verify(inspector).startBlocking();
    }

    @Test
    public final void attach_again()
    {
        when(inspector.getRuntime()).thenReturn(runtime);
        doNothing().when(inspector).detach();
        doNothing().when(inspector).setRuntime(runtime);
        doNothing().when(inspector).startBlocking();

        inspector.attach(runtime);
        verify(inspector).detach();
        verify(inspector).setRuntime(runtime);
        verify(inspector).startBlocking();
    }

    @Test
    public final void detach() throws InterruptedException
    {
        doNothing().when(inspector).stop(anyInt());
        when(inspector.getRuntime()).thenReturn(runtime);

        inspector.detach();
        verify(inspector).stop(3000);
        verify(runtime).onInspectorDetached(inspector);
    }

    @Test
    public final void onOpen_onMessage_onClose() throws InterruptedException, URISyntaxException
    {
        CountDownLatch openCountDown = new CountDownLatch(1);
        CountDownLatch firstMessageCountDown = new CountDownLatch(1);
        CountDownLatch secondMessageCountDown = new CountDownLatch(1);
        String messageToServer = "Some message for the server";
        String messageToClient = "Some message for the client";

        String[] receivedMessage = new String[1];
        ClientForTest clientForTest = new ClientForTest(inspector.getAddress())
        {
            @Override
            public void onOpen(ServerHandshake serverHandshake)
            {
                openCountDown.countDown();

                ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                scheduler.schedule(() -> {
                    send(messageToServer);
                    // Wait a little more to ensure the message has reached server
                    scheduler.schedule(firstMessageCountDown::countDown, 500, TimeUnit.MILLISECONDS);
                }, 500, TimeUnit.MILLISECONDS);
            }

            @Override
            public void onMessage(String s)
            {
                receivedMessage[0] = s;
                secondMessageCountDown.countDown();
            }
        };

        inspector.attach(runtime);
        clientForTest.connectBlocking(3, TimeUnit.SECONDS);

        try
        {
            openCountDown.await();
            verify(inspector).newMessageHandler(any(), eq(runtime));
            firstMessageCountDown.await();
            verify(inspector).onMessage(any(), eq(messageToServer));
            ((JSBaseInspectorForTest<?>)inspector).connection.send(messageToClient);
            secondMessageCountDown.await();
            assertEquals(messageToClient, receivedMessage[0]);
        }
        finally
        {
            clientForTest.close();
            inspector.detach();
        }
    }

    @Test
    public final void hashCode_hasRuntime()
    {
        inspector = new JSBaseInspectorForTest<>(port);
        inspector.setRuntime(runtime);

        assertEquals((109 * port) ^ runtime.hashCode(), inspector.hashCode());
    }

    @Test
    public final void hashCode_noRuntime()
    {
        inspector = new JSBaseInspectorForTest<>(port);

        assertEquals(109 * port, inspector.hashCode());
    }
}
