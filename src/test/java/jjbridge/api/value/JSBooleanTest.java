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
public class JSBooleanTest
{
    private static final boolean value = true;

    private JSBoolean primitive;
    @Mock private ValueGetter<Boolean> getter;
    @Mock private ValueSetter<Boolean> setter;

    @BeforeEach
    public void before() {
        primitive = new JSBoolean(getter, setter);
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
        assertNotEquals(primitive, new JSBoolean(null, null));
        assertNotEquals(primitive, new JSBoolean(getter, null));
        assertNotEquals(primitive, new JSBoolean(null, setter));
        assertEquals(primitive, new JSBoolean(getter, setter));
    }
}
