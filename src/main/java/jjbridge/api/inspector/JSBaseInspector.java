package jjbridge.api.inspector;

import jjbridge.api.runtime.JSRuntime;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * To connect Chrome DevTools, open Chrome to
 * chrome-devtools://devtools/bundled/inspector.html?v8only=true&ws=IP:PORT
 */
public abstract class JSBaseInspector<R extends JSRuntime> extends WebSocketServer implements JSInspector
{
    private R runtime;
    private MessageHandler handler;
    private final AtomicBoolean clientConnected;

    protected JSBaseInspector(int port)
    {
        super(new InetSocketAddress(port));
        this.clientConnected = new AtomicBoolean(false);
    }

    @Override
    public void onStart()
    {
    }

    @Override
    public void onOpen(WebSocket conn, ClientHandshake handshake)
    {
        handler = newMessageHandler(new Connection(conn), getRuntime());
        clientConnected.lazySet(true);
    }

    @Override
    public void onClose(WebSocket conn, int code, String reason, boolean remote)
    {
        if (handler != null)
        {
            handler.close();
            handler = null;
        }
    }

    @Override
    public void onMessage(WebSocket conn, String message)
    {
        if (handler != null)
        {
            handler.sendToRuntime(message);
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

    @Override
    @SuppressWarnings("unchecked")
    public PendingConnection attach(JSRuntime runtime)
    {
        if (getRuntime() != null)
        {
            detach();
        }
        setRuntime((R) runtime);
        start();

        return maxWaitMillis ->
        {
            long t = System.currentTimeMillis() + maxWaitMillis;
            while (!clientConnected.get() && System.currentTimeMillis() < t)
            {
                try
                {
                    Thread.sleep(maxWaitMillis / 10);
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }
            }
        };
    }

    @Override
    public void detach()
    {
        try
        {
            stop();
        }
        catch (IOException | InterruptedException e)
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
