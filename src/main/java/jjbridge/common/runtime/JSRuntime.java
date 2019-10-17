package jjbridge.common.runtime;

import jjbridge.common.inspector.JSInspector;
import jjbridge.common.value.JSObject;
import jjbridge.common.value.JSType;
import jjbridge.common.value.JSValue;

public interface JSRuntime extends AutoCloseable {
    JSObject globalObject();

    JSReference executeScript(String script);

    JSReference executeScript(String fileName, String script);

    JSReference newReference(JSType type);

    <T extends JSValue> T resolveReference(JSReference reference);

    <T extends JSValue> T resolveReference(JSReference reference, TypeResolution typeResolution);

    void onInspectorAttached(JSInspector inspector);

    void onInspectorDetached(JSInspector inspector);
}
