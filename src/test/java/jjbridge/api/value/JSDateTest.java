package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.value.strategy.ObjectPropertyGetter;
import jjbridge.api.value.strategy.ObjectPropertySetter;
import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JSDateTest {
    private static final Date value = new Date(1645980732);
    private static final ObjectPropertyGetter<JSReference> propertyGetter = name -> null;
    private static final ObjectPropertySetter<JSReference> propertySetter = (name, value1) -> {
    };

    private JSDate<JSReference> date;
    @Mock private ValueGetter<Date> getter;
    @Mock private ValueSetter<Date> setter;

    @BeforeEach
    public void before() {
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

    @Test
    public void HashCode() {
        assertEquals(date.hashCode(), date.hashCode());
        assertNotEquals(date.hashCode(), 0);
        assertEquals(date.hashCode(), new JSDate<>(getter, setter, propertyGetter, propertySetter).hashCode());
    }
}
