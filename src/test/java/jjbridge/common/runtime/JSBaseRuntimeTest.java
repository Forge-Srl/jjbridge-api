package jjbridge.common.runtime;

import jjbridge.common.inspector.JSInspector;
import jjbridge.common.value.JSType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JSBaseRuntimeTest {
    private static final String script = "some script";
    private static final String scriptName = "scriptName";
    private static final JSType jsType = JSType.Object;
    @Mock private static JSReference mockedReference;
    @Mock private static JSInspector mockedInspector1;
    @Mock private static JSInspector mockedInspector2;
    @Spy private JSBaseRuntime<JSReference> jsRuntime;

    @Test
    public final void isClosed() {
        when(jsRuntime.isClosed()).thenCallRealMethod();
        assertFalse(jsRuntime.isClosed());
    }

    @Test
    public final void close() {
        doCallRealMethod().when(jsRuntime).onInspectorAttached(mockedInspector1);
        jsRuntime.onInspectorAttached(mockedInspector1);
        doCallRealMethod().when(jsRuntime).onInspectorAttached(mockedInspector2);
        jsRuntime.onInspectorAttached(mockedInspector2);
        doCallRealMethod().when(jsRuntime).close();
        when(jsRuntime.isClosed()).thenCallRealMethod();

        jsRuntime.close();
        verify(mockedInspector1).detach();
        verify(mockedInspector2).detach();
        assertTrue(jsRuntime.isClosed());
    }

    @Test
    public final void globalObject() {
        when(jsRuntime.isClosed()).thenReturn(false);
        jsRuntime.globalObject();
        verify(jsRuntime).getGlobalObject();
    }

    @Test()
    public final void globalObject_throwsException() {
        when(jsRuntime.isClosed()).thenReturn(true);
        assertThrows(RuntimeException.class, () -> jsRuntime.globalObject());
    }

    @Test
    public final void executeScript() {
        when(jsRuntime.isClosed()).thenReturn(false);
        when(jsRuntime.runScript(scriptName, script)).thenReturn(null);

        assertNull(jsRuntime.executeScript(scriptName, script));
    }

    @Test()
    public final void executeScript_throwsException() {
        when(jsRuntime.isClosed()).thenReturn(true);
        assertThrows(RuntimeException.class, () -> jsRuntime.executeScript(scriptName, script));
    }

    @Test
    public final void executeScript_noName() {
        when(jsRuntime.isClosed()).thenReturn(false);
        when(jsRuntime.runScript("/script_0", script)).thenReturn(null);
        assertNull(jsRuntime.executeScript(script));
        when(jsRuntime.runScript("/script_1", script)).thenReturn(null);
        assertNull(jsRuntime.executeScript(script));
        when(jsRuntime.runScript("/script_2", script)).thenReturn(null);
        assertNull(jsRuntime.executeScript(script));
    }

    @Test
    public final void newReference() {
        when(jsRuntime.isClosed()).thenReturn(false);
        when(jsRuntime.createNewReference(jsType)).thenReturn(null);

        assertNull(jsRuntime.newReference(jsType));
    }

    @Test()
    public final void newReference_throwsException() {
        when(jsRuntime.isClosed()).thenReturn(true);
        assertThrows(RuntimeException.class, () -> jsRuntime.newReference(jsType));
    }

    @Test
    public final void resolveReference() {
        jsRuntime.resolveReference(mockedReference);
        verify(jsRuntime).resolveReference(mockedReference, TypeResolution.Nominal);
    }

    @Test
    public final void resolveReference_fullParams_nominalType() {
        when(jsRuntime.isClosed()).thenReturn(false);
        when(mockedReference.getNominalType()).thenReturn(jsType);
        jsRuntime.resolveReference(mockedReference, TypeResolution.Nominal);
        verify(jsRuntime).resolve(mockedReference, jsType);
    }

    @Test
    public final void resolveReference_fullParams_actualType() {
        when(jsRuntime.isClosed()).thenReturn(false);
        when(mockedReference.getActualType()).thenReturn(jsType);
        jsRuntime.resolveReference(mockedReference, TypeResolution.Actual);
        verify(jsRuntime).resolve(mockedReference, jsType);
    }

    @Test()
    public final void resolveReference_fullParams_throwsException() {
        when(jsRuntime.isClosed()).thenReturn(true);
        assertThrows(RuntimeException.class, () -> jsRuntime.resolveReference(mockedReference, TypeResolution.Nominal));
    }

    @Test()
    public final void onInspectorAttached_ThrowsException() {
        jsRuntime.onInspectorAttached(mockedInspector1);
        jsRuntime.onInspectorAttached(mockedInspector2);
        assertThrows(IllegalStateException.class, () -> jsRuntime.onInspectorAttached(mockedInspector1));
    }

    @Test
    public final void onInspectorAttached_Detached() {
        assertDoesNotThrow(() -> {
            jsRuntime.onInspectorAttached(mockedInspector1);
            jsRuntime.onInspectorAttached(mockedInspector2);
            jsRuntime.onInspectorDetached(mockedInspector1);
            jsRuntime.onInspectorAttached(mockedInspector1);
        });
    }
}
