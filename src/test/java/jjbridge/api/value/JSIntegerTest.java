package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JSIntegerTest
{
    private static final long value = 69420;

    private JSInteger primitive;
    @Mock private ValueGetter<Long> getter;
    @Mock private ValueSetter<Long> setter;

    @BeforeEach
    public void before() {
        primitive = new JSInteger(getter, setter);
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
        assertEquals(JSInteger.MAX_SAFE_INTEGER, 9007199254740991L);
    }

    @Test
    public void MIN_SAFE_INTEGER() {
        assertEquals(JSInteger.MIN_SAFE_INTEGER, -9007199254740991L);
    }

    @Test
    public void isSafeJSInteger() {
        assertTrue(JSInteger.isSafeJSInteger(JSInteger.MAX_SAFE_INTEGER));
        assertTrue(JSInteger.isSafeJSInteger(JSInteger.MIN_SAFE_INTEGER));
        assertTrue(JSInteger.isSafeJSInteger(0));
        assertFalse(JSInteger.isSafeJSInteger(JSInteger.MAX_SAFE_INTEGER + 1));
        assertFalse(JSInteger.isSafeJSInteger(JSInteger.MIN_SAFE_INTEGER - 1));
        assertFalse(JSInteger.isSafeJSInteger(Long.MAX_VALUE));
        assertFalse(JSInteger.isSafeJSInteger(Long.MIN_VALUE));
    }

    @Test
    public void Equals() {
        assertEquals(primitive, primitive);
        assertNotEquals(primitive, null);
        assertNotEquals(primitive, new JSInteger(null, null));
        assertNotEquals(primitive, new JSInteger(getter, null));
        assertNotEquals(primitive, new JSInteger(null, setter));
        assertEquals(primitive, new JSInteger(getter, setter));
    }
}
