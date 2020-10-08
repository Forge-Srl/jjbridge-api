package jjbridge.api.inspector;

/**
 * This class allows to send messages to both the inspector client and the inspector server attached to a runtime.
 * */
public abstract class MessageHandler
{
    private final Connection connection;

    public MessageHandler(Connection connection)
    {
        this.connection = connection;
    }

    public void sendToInspector(String message)
    {
        connection.send(message);
    }

    public abstract void sendToRuntime(String message);

    public abstract void close();
}
