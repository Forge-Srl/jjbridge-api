package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.value.strategy.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
    @Mock private ObjectPropertyGetter<JSReference> getter;
    @Mock private ObjectPropertySetter<JSReference> setter;

    @BeforeEach
    public void before() {
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

    @Test
    public void HashCode() {
        assertEquals(object.hashCode(), object.hashCode());
        assertNotEquals(object.hashCode(), 0);
        assertEquals(object.hashCode(), new JSObject<>(getter, setter).hashCode());
    }
}
