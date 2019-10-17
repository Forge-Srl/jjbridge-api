package jjbridge.common.value;

import jjbridge.common.runtime.JSReference;
import jjbridge.common.value.strategy.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

public class JSObjectTest {
    private static final String propertyName = "name";
    private static final JSReference propertyValue = new JSReference() {
        @Override
        public JSType getNominalType() {
            return null;
        }

        @Override
        public JSType getActualType() {
            return null;
        }
    };

    private JSObject<JSReference> object;
    private ObjectPropertyGetter<JSReference> getter;
    private ObjectPropertySetter<JSReference> setter;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        getter = (ObjectPropertyGetter<JSReference>) mock(ObjectPropertyGetter.class);
        setter = (ObjectPropertySetter<JSReference>) mock(ObjectPropertySetter.class);
        object = new JSObject<>(getter, setter);
    }

    @Test
    public void get() {
        when(getter.getPropertyByName(propertyName)).thenReturn(propertyValue);
        assertEquals(propertyValue, object.get(propertyName));
    }

    @Test
    public void set() {
        object.set(propertyName, propertyValue);
        verify(setter).setPropertyByName(propertyName, propertyValue);
    }

    @Test
    public void Equals() {
        assertEquals(object, object);
        assertNotEquals(object, null);
        assertNotEquals(object, new JSObject<>(null, null));
        assertNotEquals(object, new JSObject<>(getter, null));
        assertNotEquals(object, new JSObject<>(null, setter));
        assertEquals(object, new JSObject<>(getter, setter));
    }
}
