package jjbridge.common.inspector;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class MessageHandlerTest {
    private static class MessageHandlerForTest extends MessageHandler {
        MessageHandlerForTest(Connection connection) {
            super(connection);
        }

        @Override
        public void sendToRuntime(String message) {
        }

        @Override
        public void close() {
        }
    }

    @Mock private Connection connection;

    @Test
    public final void sendToInspector() {
        MessageHandler handler = new MessageHandlerForTest(connection);
        String message = "message";
        handler.sendToInspector(message);
        verify(connection).send(message);
    }
}
