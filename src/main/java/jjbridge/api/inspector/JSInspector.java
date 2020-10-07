package jjbridge.api.inspector;

import jjbridge.api.runtime.JSRuntime;

public interface JSInspector
{
    PendingConnection attach(JSRuntime runtime);

    void detach();
}
