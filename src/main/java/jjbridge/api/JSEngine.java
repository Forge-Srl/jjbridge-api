package jjbridge.api;

import jjbridge.api.inspector.JSInspector;
import jjbridge.api.runtime.JSRuntime;

public interface JSEngine
{
    JSRuntime newRuntime();

    JSInspector newInspector(int port);
}
