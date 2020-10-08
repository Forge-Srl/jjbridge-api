package jjbridge.api.inspector;

import org.java_websocket.WebSocket;

/**
 * The connection between an inspector client and the inspector server attached to a JavaScript runtime.
 * */
public class Connection
{
    private final WebSocket socket;

    public Connection(WebSocket socket)
    {
        this.socket = socket;
    }

    /**
     * Sends the given message to the inspector client.
     *
     * @param message the message to be sent
     * */
    public void send(String message)
    {
        socket.send(message);
    }
}
