package jjbridge.common.inspector;

import org.junit.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class MessageHandlerTest {
    private class MessageHandlerForTest extends MessageHandler {
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

    @Test
    public final void sendToInspector() {
        Connection connection = mock(Connection.class);
        MessageHandler handler = new MessageHandlerForTest(connection);
        String message = "message";
        handler.sendToInspector(message);
        verify(connection).send(message);
    }
}
