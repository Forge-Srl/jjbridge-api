package jjbridge.common.inspector;

import org.java_websocket.WebSocket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ConnectionTest {
    @Mock private WebSocket socket;

    @Test
    public final void send() {
        Connection connection = new Connection(socket);
        String message = "message";
        connection.send(message);
        verify(socket).send(message);
    }
}
