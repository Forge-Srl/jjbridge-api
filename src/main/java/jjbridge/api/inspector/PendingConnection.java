package jjbridge.api.inspector;

public interface PendingConnection
{
    void waitForConnection(long maxWaitMillis);
}
