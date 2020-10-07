package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.value.strategy.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JSFunctionTest {
    private static final ObjectPropertyGetter<JSReference> propertyGetter = name -> null;
    private static final ObjectPropertySetter<JSReference> propertySetter = (name, value) -> {
    };
    private static final JSReference reference = new JSReference() {
        @Override
        public JSType getNominalType() {
            return null;
        }

        @Override
        public JSType getActualType() {
            return null;
        }
    };

    private JSFunction<JSReference> function;
    @Mock private FunctionInvoker<JSReference> invoker;
    @Mock private FunctionSetter<JSReference> setter;

    @BeforeEach
    public void before() {
        function = new JSFunction<>(JSReference.class, propertyGetter, propertySetter, invoker, setter);
    }

    @Test
    public void invoke() {
        when(invoker.invokeFunction(reference, new JSReference[]{null, reference})).thenReturn(reference);
        assertEquals(reference, function.invoke(reference, null, reference));
    }

    @Test
    public void invokeConstructor() {
        when(invoker.invokeConstructor(new JSReference[]{null, reference})).thenReturn(reference);
        assertEquals(reference, function.invokeConstructor(null, reference));
    }

    @Test
    public void setFunction() {
        FunctionCallback<JSReference> functionCallback = arguments -> null;
        function.setFunction(functionCallback);
        verify(setter).setFunction(functionCallback);
    }

    @Test
    public void Equals() {
        assertEquals(function, function);
        assertNotEquals(function, null);
        assertNotEquals(function, new JSFunction<>(null, propertyGetter, propertySetter, null, null));
        assertNotEquals(function, new JSFunction<>(null, propertyGetter, propertySetter, null, setter));
        assertNotEquals(function, new JSFunction<>(null, propertyGetter, propertySetter, invoker, null));
        assertNotEquals(function, new JSFunction<>(null, null, propertySetter, invoker, setter));
        assertEquals(function, new JSFunction<>(null, propertyGetter, propertySetter, invoker, setter));
    }

    @Test
    public void HashCode() {
        assertEquals(function.hashCode(), function.hashCode());
        assertNotEquals(function.hashCode(), 0);
        assertEquals(function.hashCode(), new JSFunction<>(null, propertyGetter, propertySetter, invoker, setter).hashCode());
    }
}
