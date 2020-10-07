package jjbridge.api.runtime;

import jjbridge.api.inspector.JSInspector;
import jjbridge.api.value.JSObject;
import jjbridge.api.value.JSType;
import jjbridge.api.value.JSValue;

public interface JSRuntime extends AutoCloseable
{
    JSObject<?> globalObject();

    JSReference executeScript(String script);

    JSReference executeScript(String fileName, String script);

    JSReference newReference(JSType type);

    <T extends JSValue> T resolveReference(JSReference reference);

    <T extends JSValue> T resolveReference(JSReference reference, TypeResolution typeResolution);

    void onInspectorAttached(JSInspector inspector);

    void onInspectorDetached(JSInspector inspector);
}
