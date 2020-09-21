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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JSStringTest
{
    private static final String value = "Some string";

    private JSString primitive;
    @Mock private ValueGetter<String> getter;
    @Mock private ValueSetter<String> setter;

    @BeforeEach
    public void before() {
        primitive = new JSString(getter, setter);
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
        assertNotEquals(primitive, new JSString(null, null));
        assertNotEquals(primitive, new JSString(getter, null));
        assertNotEquals(primitive, new JSString(null, setter));
        assertEquals(primitive, new JSString(getter, setter));
    }
}
