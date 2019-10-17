package jjbridge.common.inspector;

import org.java_websocket.WebSocket;
import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class ConnectionTest {
    @Test
    public final void send() {
        WebSocket socket = mock(WebSocket.class);
        Connection connection = new Connection(socket);
        String message = "message";
        connection.send(message);
        verify(socket).send(message);
    }
}
