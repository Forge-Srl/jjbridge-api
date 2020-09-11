package jjbridge.common.inspector;

public interface PendingConnection
{
    void waitForConnection(long maxWaitMillis);
}
