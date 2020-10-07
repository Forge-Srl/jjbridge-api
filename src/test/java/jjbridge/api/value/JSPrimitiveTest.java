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
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JSPrimitiveTest {
    private static final float value = 0.786f;

    private JSPrimitive<Float> primitive;
    @Mock private ValueGetter<Float> getter;
    @Mock private ValueSetter<Float> setter;

    @BeforeEach
    public void before() {
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

    @Test
    public void HashCode() {
        assertEquals(primitive.hashCode(), primitive.hashCode());
        assertNotEquals(primitive.hashCode(), 0);
        assertEquals(primitive.hashCode(), new JSPrimitive<Float>(getter, setter) {
        }.hashCode());
    }
}
