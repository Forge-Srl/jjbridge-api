package jjbridge.common.value;

import jjbridge.common.runtime.JSReference;
import jjbridge.common.value.strategy.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

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
    private FunctionInvoker<JSReference> invoker;
    private FunctionSetter<JSReference> setter;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        invoker = (FunctionInvoker<JSReference>) mock(FunctionInvoker.class);
        setter = (FunctionSetter<JSReference>) mock(FunctionSetter.class);
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
}
