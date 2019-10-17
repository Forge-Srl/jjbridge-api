package jjbridge.common.value;

import jjbridge.common.runtime.JSReference;
import jjbridge.common.value.strategy.ObjectPropertyGetter;
import jjbridge.common.value.strategy.ObjectPropertySetter;
import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class JSDateTest {
    private static final Date value = new Date(1645980732);
    private static final ObjectPropertyGetter<JSReference> propertyGetter = name -> null;
    private static final ObjectPropertySetter<JSReference> propertySetter = (name, value1) -> {
    };

    private JSDate<JSReference> date;
    private ValueGetter<Date> getter;
    private ValueSetter<Date> setter;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        getter = (ValueGetter<Date>) mock(ValueGetter.class);
        setter = (ValueSetter<Date>) mock(ValueSetter.class);
        date = new JSDate<>(getter, setter, propertyGetter, propertySetter);
    }

    @Test
    public void get() {
        when(getter.getValue()).thenReturn(value);
        assertEquals(value, date.getValue());
    }

    @Test
    public void set() {
        date.setValue(value);
        verify(setter).setValue(value);
    }

    @Test
    public void Equals() {
        assertEquals(date, date);
        assertNotEquals(date, null);
        assertNotEquals(date, new JSDate<>(null, null, propertyGetter, propertySetter));
        assertNotEquals(date, new JSDate<>(getter, null, propertyGetter, propertySetter));
        assertNotEquals(date, new JSDate<>(null, setter, propertyGetter, propertySetter));
        assertNotEquals(date, new JSDate<>(getter, setter, null, propertySetter));
        assertEquals(date, new JSDate<>(getter, setter, propertyGetter, propertySetter));
    }
}
