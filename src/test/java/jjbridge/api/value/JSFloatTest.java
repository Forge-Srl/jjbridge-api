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
public class JSFloatTest
{
    private static final double value = 69.420;

    private JSFloat primitive;
    @Mock private ValueGetter<Double> getter;
    @Mock private ValueSetter<Double> setter;

    @BeforeEach
    public void before() {
        primitive = new JSFloat(getter, setter);
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
        assertNotEquals(primitive, new JSFloat(null, null));
        assertNotEquals(primitive, new JSFloat(getter, null));
        assertNotEquals(primitive, new JSFloat(null, setter));
        assertEquals(primitive, new JSFloat(getter, setter));
    }
}
