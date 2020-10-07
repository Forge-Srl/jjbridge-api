package jjbridge.api.inspector;

import org.java_websocket.WebSocket;

public class Connection
{
    private final WebSocket socket;

    public Connection(WebSocket socket)
    {
        this.socket = socket;
    }

    public void send(String message)
    {
        socket.send(message);
    }
}
