package jjbridge.api.inspector;

import jjbridge.api.runtime.JSRuntime;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.CountDownLatch;

/**
 * The base implementation of a JavaScript runtime inspector.
 * <p>This class already implements the communication layer with the inspector client via Web Socket. Subclasses should
 * only define how incoming messages are delivered to the inspector server controlling the runtime.</p>
 * <p>Communication must conform to the protocol defined at
 * <a href="https://chromedevtools.github.io/devtools-protocol/">Chrome DevTools specification</a></p>
 * <br>
 * <p>To use the inspector, open Chromium at
 * <a href="chrome-devtools://devtools/bundled/inspector.html?v8only=true&ws=IP:PORT">
 * chrome-devtools://devtools/bundled/inspector.html?v8only=true&amp;ws=IP:PORT</a> where:</p>
 * <ul>
 *     <li>{@code IP} is the IP address of the machine running the JavaScript engine</li>
 *     <li>{@code PORT} is the port passed in {@link #JSBaseInspector(int)}</li>
 * </ul>
 */
public abstract class JSBaseInspector<R extends JSRuntime> extends WebSocketServer implements JSInspector
{
    private final Object lock = new Object();
    private CountDownLatch startLatch;
    private R runtime;
    private MessageHandler handler;

    protected JSBaseInspector(int port)
    {
        super(new InetSocketAddress(port));
        setReuseAddr(true);
    }

    @Override
    public void onStart()
    {
        startLatch.countDown();
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        synchronized (lock)
        {
            if (handler != null)
            {
                handler.close();
                handler = null;
            }
            handler = newMessageHandler(new Connection(conn), getRuntime());
        }
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        synchronized (lock)
        {
            if (handler != null)
            {
                handler.close();
                handler = null;
            }
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message)
    {
        synchronized (lock)
        {
            if (handler != null)
            {
                handler.sendToRuntime(message);
            }
        }
    }

    @Override
    public void onError(WebSocket conn, Exception ex)
    {
        throw new RuntimeException(ex);
    }

    R getRuntime()
    {
        return runtime;
    }

    void setRuntime(R runtime)
    {
        this.runtime = runtime;
        this.runtime.onInspectorAttached(this);
    }

    void startBlocking()
    {
        startLatch = new CountDownLatch(1);
        start();
        try
        {
            startLatch.await();
        }
        catch (InterruptedException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public void attach(JSRuntime runtime)
    {
        if (getRuntime() != null)
        {
            detach();
        }
        setRuntime((R) runtime);
        startBlocking();
    }

    @Override
    public void detach()
    {
        try
        {
            synchronized (lock)
            {
                if (handler != null)
                {
                    handler.close();
                    handler = null;
                }
                stop(3000);
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        getRuntime().onInspectorDetached(this);
    }

    protected abstract MessageHandler newMessageHandler(Connection connection, R runtime);

    @Override
    public int hashCode()
    {
        R runtime = getRuntime();
        return (109 * getPort()) ^ (runtime == null ? 0 : runtime.hashCode());
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof JSBaseInspector))
        {
            return false;
        }

        @SuppressWarnings("unchecked")
        JSBaseInspector<R> that = (JSBaseInspector<R>) o;

        return getPort() == that.getPort() && Objects.equals(runtime, that.runtime);
    }
}
