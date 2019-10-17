package jjbridge.common.value;

import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class JSPrimitiveTest {
    private static final float value = 0.786f;

    private JSPrimitive<Float> primitive;
    private ValueGetter<Float> getter;
    private ValueSetter<Float> setter;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        getter = (ValueGetter<Float>) mock(ValueGetter.class);
        setter = (ValueSetter<Float>) mock(ValueSetter.class);
        primitive = new JSPrimitive<Float>(getter, setter) {
        };
    }

    @Test
    public void getValue() {
        when(getter.getValue()).thenReturn(value);
        assertEquals(value, primitive.getValue(), 0);
    }

    @Test
    public void setValue() {
        primitive.setValue(value);
        verify(setter).setValue(value);
    }

    @Test
    public void Equals() {
        assertEquals(primitive, primitive);
        assertNotEquals(primitive, null);
        assertNotEquals(primitive, new JSPrimitive<Float>(null, null) {
        });
        assertNotEquals(primitive, new JSPrimitive<Float>(getter, null) {
        });
        assertNotEquals(primitive, new JSPrimitive<Float>(null, setter) {
        });
        assertEquals(primitive, new JSPrimitive<Float>(getter, setter) {
        });
    }
}
