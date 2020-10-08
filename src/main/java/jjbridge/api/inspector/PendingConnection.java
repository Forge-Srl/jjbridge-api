package jjbridge.api.inspector;

/**
 * A pending connection allows to wait for a client to be connected.*/
public interface PendingConnection
{
    /**
     * Use this method to wait for a connection.
     * <p>Invoking this method should prevent the execution of further code until a connection is made or the specified
     * time is elapsed.</p>
     *
     * @param maxWaitMillis the maximum time (in milliseconds) to wait for a connection.
     * */
    void waitForConnection(long maxWaitMillis);
}
