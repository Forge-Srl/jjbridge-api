package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JSIntegerTest
{
    private static final int value = 69420;

    private JSInteger primitive;
    @Mock private ValueGetter<Integer> getter;
    @Mock private ValueSetter<Integer> setter;

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
    public void Equals() {
        assertEquals(primitive, primitive);
        assertNotEquals(primitive, null);
        assertNotEquals(primitive, new JSInteger(null, null));
        assertNotEquals(primitive, new JSInteger(getter, null));
        assertNotEquals(primitive, new JSInteger(null, setter));
        assertEquals(primitive, new JSInteger(getter, setter));
    }
}
