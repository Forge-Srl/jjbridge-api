package jjbridge.common.inspector;

public abstract class MessageHandler {
    private final Connection connection;

    public MessageHandler(Connection connection) {
        this.connection = connection;
    }

    public void sendToInspector(String message) {
        connection.send(message);
    }

    public abstract void sendToRuntime(String message);

    public abstract void close();
}
