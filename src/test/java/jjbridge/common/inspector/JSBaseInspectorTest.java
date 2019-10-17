package jjbridge.common.inspector;

import jjbridge.common.runtime.JSRuntime;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class JSBaseInspectorTest {
    private int port;
    private JSRuntime runtime;
    private JSBaseInspector<JSRuntime> inspector;

    private class JSBaseInspectorForTest<R extends JSRuntime> extends JSBaseInspector<R> {
        JSBaseInspectorForTest(int port) {
            super(port);
        }

        @Override
        protected MessageHandler newMessageHandler(Connection connection, JSRuntime runtime) {
            return null;
        }
    }

    @Before
    public final void before() {
        port = 1000;
        runtime = mock(JSRuntime.class);
        inspector = spy(new JSBaseInspectorForTest<>(port));
    }

    @Test
    public final void onOpen_onMessage_onClose() {
        String message = "message";
        MessageHandler messageHandler = mock(MessageHandler.class);

        when(inspector.getRuntime()).thenReturn(null);
        when(inspector.newMessageHandler(any(Connection.class), eq(null))).thenReturn(messageHandler);
        inspector.onOpen(null, null);
        verify(inspector).newMessageHandler(any(Connection.class), eq(null));

        inspector.onMessage(null, message);
        verify(messageHandler).sendToRuntime(message);

        inspector.onClose(null, 0, null, false);
        verify(messageHandler).close();
    }

    @Test(expected = RuntimeException.class)
    public final void onError() {
        inspector.onError(null, null);
    }

    @Test
    public final void setRuntime() {
        inspector.setRuntime(runtime);
        verify(runtime).onInspectorAttached(inspector);
    }

    @Test
    public final void attach_firstTime() {
        when(inspector.getRuntime()).thenReturn(null);
        doNothing().when(inspector).setRuntime(runtime);
        doNothing().when(inspector).start();

        PendingConnection pendingConnection = inspector.attach(runtime);
        verify(inspector).setRuntime(runtime);
        verify(inspector).start();

        int maxWaitMillis = 500;
        long startingTime = System.currentTimeMillis();
        pendingConnection.waitForConnection(maxWaitMillis);
        long endingTime = System.currentTimeMillis();
        assertTrue(endingTime - startingTime >= maxWaitMillis);
    }

    @Test
    public final void attach_again() {
        when(inspector.getRuntime()).thenReturn(runtime);
        doNothing().when(inspector).detach();
        doNothing().when(inspector).setRuntime(runtime);
        doNothing().when(inspector).start();

        PendingConnection pendingConnection = inspector.attach(runtime);
        verify(inspector).detach();
        verify(inspector).setRuntime(runtime);
        verify(inspector).start();

        int maxWaitMillis = 500;
        long startingTime = System.currentTimeMillis();
        pendingConnection.waitForConnection(maxWaitMillis);
        long endingTime = System.currentTimeMillis();
        assertTrue(endingTime - startingTime >= maxWaitMillis);
    }

    @Test
    public final void detach() throws IOException, InterruptedException {
        doNothing().when(inspector).stop();
        when(inspector.getRuntime()).thenReturn(runtime);

        inspector.detach();
        verify(inspector).stop();
        verify(runtime).onInspectorDetached(inspector);
    }

    @Test
    public final void hashCode_hasRuntime() {
        inspector = new JSBaseInspectorForTest<>(port);
        inspector.setRuntime(runtime);

        assertEquals((109 * port) ^ runtime.hashCode(), inspector.hashCode());
    }

    @Test
    public final void hashCode_noRuntime() {
        inspector = new JSBaseInspectorForTest<>(port);

        assertEquals(109 * port, inspector.hashCode());
    }
}
