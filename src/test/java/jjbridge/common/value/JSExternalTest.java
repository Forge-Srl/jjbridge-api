package jjbridge.common.value;

import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class JSExternalTest {
    private static final Object value = new Object();

    private JSExternal<Object> external;
    private ValueGetter<Object> getter;
    private ValueSetter<Object> setter;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        getter = (ValueGetter<Object>) mock(ValueGetter.class);
        setter = (ValueSetter<Object>) mock(ValueSetter.class);
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
}
