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
public class JSExternalTest {
    private static final Object value = new Object();

    private JSExternal<Object> external;
    @Mock private ValueGetter<Object> getter;
    @Mock private ValueSetter<Object> setter;

    @BeforeEach
    public void before() {
        external = new JSExternal<>(getter, setter);
    }

    @Test
    public void getValue() {
        when(getter.getValue()).thenReturn(value);
        assertEquals(value, external.getValue());
    }

    @Test
    public void setValue() {
        external.setValue(value);
        verify(setter).setValue(value);
    }

    @Test
    public void Equals() {
        assertEquals(external, external);
        assertNotEquals(external, null);
        assertNotEquals(external, new JSExternal<>(null, null));
        assertNotEquals(external, new JSExternal<>(getter, null));
        assertNotEquals(external, new JSExternal<>(null, setter));
        assertEquals(external, new JSExternal<>(getter, setter));
    }

    @Test
    public void HashCode() {
        assertEquals(external.hashCode(), external.hashCode());
        assertNotEquals(external.hashCode(), 0);
        assertEquals(external.hashCode(), new JSExternal<>(getter, setter).hashCode());
    }
}
