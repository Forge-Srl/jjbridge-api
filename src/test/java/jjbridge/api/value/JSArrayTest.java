package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.value.strategy.ArrayDataGetter;
import jjbridge.api.value.strategy.ArrayDataSetter;
import jjbridge.api.value.strategy.ObjectPropertyGetter;
import jjbridge.api.value.strategy.ObjectPropertySetter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class JSArrayTest {
    private static final ObjectPropertyGetter<JSReference> propertyGetter = name -> null;
    private static final ObjectPropertySetter<JSReference> propertySetter = (name, value) -> {
    };
    private static final int position = 15;
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

    @Mock private ArrayDataGetter<JSReference> arrayDataGetter;
    @Mock private ArrayDataSetter<JSReference> arrayDataSetter;
    private JSArray<JSReference> array;

    @BeforeEach
    public void before() {
        array = new JSArray<>(propertyGetter, propertySetter, arrayDataGetter, arrayDataSetter);
    }

    @Test
    public void size() {
        int expectedSize = 309;
        when(arrayDataGetter.getSize()).thenReturn(expectedSize);
        assertEquals(expectedSize, array.size());
    }

    @Test
    public void get() {
        when(arrayDataGetter.getItemByPosition(position)).thenReturn(propertyValue);
        assertEquals(propertyValue, array.get(position));
    }

    @Test
    public void set() {
        array.set(position, propertyValue);
        verify(arrayDataSetter).setItemByPosition(position, propertyValue);
    }

    @Test
    public void Equals() {
        assertEquals(array, array);
        assertNotEquals(array, null);
        assertNotEquals(array, new JSArray<>(propertyGetter, propertySetter, null, null));
        assertNotEquals(array, new JSArray<>(propertyGetter, propertySetter, arrayDataGetter, null));
        assertNotEquals(array, new JSArray<>(propertyGetter, propertySetter, null, arrayDataSetter));
        assertNotEquals(array, new JSArray<>(propertyGetter, null, arrayDataGetter, arrayDataSetter));
        assertEquals(array, new JSArray<>(propertyGetter, propertySetter, arrayDataGetter, arrayDataSetter));
    }

    @Test
    public void HashCode() {
        assertEquals(array.hashCode(), array.hashCode());
        assertNotEquals(array.hashCode(), 0);
        assertEquals(array.hashCode(), new JSArray<>(propertyGetter, propertySetter, arrayDataGetter, arrayDataSetter).hashCode());
    }
}
