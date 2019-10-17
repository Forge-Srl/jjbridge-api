package jjbridge.common.inspector;

import jjbridge.common.runtime.JSRuntime;

public interface JSInspector {
    PendingConnection attach(JSRuntime runtime);

    void detach();
}
