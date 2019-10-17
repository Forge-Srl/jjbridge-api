package jjbridge.common;

import jjbridge.common.inspector.JSInspector;
import jjbridge.common.runtime.JSRuntime;

public interface JSEngine {
    JSRuntime newRuntime();

    JSInspector newInspector(int port);
}
