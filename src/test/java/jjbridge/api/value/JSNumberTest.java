package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JSNumberTest
{
    private static final double value = 69.420;

    private JSNumber primitive;
    @Mock private ValueGetter<Double> getter;
    @Mock private ValueSetter<Double> setter;

    @BeforeEach
    public void before() {
        primitive = new JSNumber(getter, setter);
    }

    @Test
    public void getLongValue() {
        when(getter.getValue()).thenReturn(120.0);
        assertEquals(120, primitive.getLongValue());
        when(getter.getValue()).thenReturn((double) JSNumber.MAX_SAFE_INTEGER);
        assertEquals(JSNumber.MAX_SAFE_INTEGER, primitive.getLongValue());
        when(getter.getValue()).thenReturn((double) JSNumber.MIN_SAFE_INTEGER);
        assertEquals(JSNumber.MIN_SAFE_INTEGER, primitive.getLongValue());
        when(getter.getValue()).thenReturn(100.7);
        assertEquals(100, primitive.getLongValue());
    }

    @Test
    public void setLongValue() {
        primitive.setLongValue(120L);
        verify(setter).setValue(120.0);
        primitive.setLongValue(JSNumber.MAX_SAFE_INTEGER);
        verify(setter).setValue((double) JSNumber.MAX_SAFE_INTEGER);
        primitive.setLongValue(JSNumber.MIN_SAFE_INTEGER);
        verify(setter).setValue((double) JSNumber.MIN_SAFE_INTEGER);
    }

    @Test
    public void getValue() {
        when(getter.getValue()).thenReturn(value);
        assertEquals(value, primitive.getValue());
    }

    @Test
    public void setValue() {
        primitive.setValue(value);
        verify(setter).setValue(value);
    }

    @Test
    public void MAX_SAFE_INTEGER() {
        assertEquals(JSNumber.MAX_SAFE_INTEGER, 9007199254740991L);
    }

    @Test
    public void MIN_SAFE_INTEGER() {
        assertEquals(JSNumber.MIN_SAFE_INTEGER, -9007199254740991L);
    }

    @Test
    public void isSafeJSInteger() {
        assertTrue(JSNumber.isSafeJSInteger(JSNumber.MAX_SAFE_INTEGER));
        assertTrue(JSNumber.isSafeJSInteger(JSNumber.MIN_SAFE_INTEGER));
        assertTrue(JSNumber.isSafeJSInteger(0));
        assertFalse(JSNumber.isSafeJSInteger(JSNumber.MAX_SAFE_INTEGER + 1));
        assertFalse(JSNumber.isSafeJSInteger(JSNumber.MIN_SAFE_INTEGER - 1));
        assertFalse(JSNumber.isSafeJSInteger(Long.MAX_VALUE));
        assertFalse(JSNumber.isSafeJSInteger(Long.MIN_VALUE));
    }

    @Test
    public void Equals() {
        assertEquals(primitive, primitive);
        assertNotEquals(primitive, null);
        assertNotEquals(primitive, new JSNumber(null, null));
        assertNotEquals(primitive, new JSNumber(getter, null));
        assertNotEquals(primitive, new JSNumber(null, setter));
        assertEquals(primitive, new JSNumber(getter, setter));
    }
}
