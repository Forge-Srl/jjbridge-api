package jjbridge.common.value;

import jjbridge.common.runtime.JSReference;
import jjbridge.common.value.strategy.ArrayDataGetter;
import jjbridge.common.value.strategy.ArrayDataSetter;
import jjbridge.common.value.strategy.ObjectPropertyGetter;
import jjbridge.common.value.strategy.ObjectPropertySetter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.mockito.Mockito.*;

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

    private ArrayDataGetter<JSReference> arrayDataGetter;
    private ArrayDataSetter<JSReference> arrayDataSetter;
    private JSArray<JSReference> array;

    @Before
    @SuppressWarnings("unchecked")
    public void before() {
        arrayDataGetter = (ArrayDataGetter<JSReference>) mock(ArrayDataGetter.class);
        arrayDataSetter = (ArrayDataSetter<JSReference>) mock(ArrayDataSetter.class);
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
}
