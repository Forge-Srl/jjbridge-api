package jjbridge.common.value;

import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;
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
public class JSDoubleTest
{
    private static final double value = 69.420;

    private JSDouble primitive;
    @Mock private ValueGetter<Double> getter;
    @Mock private ValueSetter<Double> setter;

    @BeforeEach
    public void before() {
        primitive = new JSDouble(getter, setter);
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
        assertNotEquals(primitive, new JSDouble(null, null));
        assertNotEquals(primitive, new JSDouble(getter, null));
        assertNotEquals(primitive, new JSDouble(null, setter));
        assertEquals(primitive, new JSDouble(getter, setter));
    }
}
