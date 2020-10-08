package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.value.strategy.ArrayDataGetter;
import jjbridge.api.value.strategy.ArrayDataSetter;
import jjbridge.api.value.strategy.ObjectPropertyGetter;
import jjbridge.api.value.strategy.ObjectPropertySetter;

/**
 * The {@code Array} type of JavaScript.
 * */
public class JSArray<R extends JSReference> extends JSObject<R>
{
    private final ArrayDataGetter<R> arrayDataGetter;
    private final ArrayDataSetter<R> arrayDataSetter;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public JSArray(ObjectPropertyGetter<R> propertyGetter, ObjectPropertySetter<R> propertySetter,
                   ArrayDataGetter<R> arrayDataGetter, ArrayDataSetter<R> arrayDataSetter)
    {
        super(propertyGetter, propertySetter);
        this.arrayDataGetter = arrayDataGetter;
        this.arrayDataSetter = arrayDataSetter;
    }

    /**
     * Computes the number of elements in this JavaScript array.
     *
     * @return the number of elements of the array
     * */
    public int size()
    {
        return this.arrayDataGetter.getSize();
    }

    /**
     * Retrieves an element of this JavaScript array.
     *
     * @param position the position of the element in the array
     * @return a reference to the element
     * */
    public JSReference get(int position)
    {
        return this.arrayDataGetter.getItemByPosition(position);
    }

    /**
     * Updates an element of this JavaScript array.
     *
     * @param position the position of the element in the array
     * @param value the new reference pointed by the element of the array
     * */
    @SuppressWarnings("unchecked")
    public void set(int position, JSReference value)
    {
        this.arrayDataSetter.setItemByPosition(position, (R) value);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof JSArray))
        {
            return false;
        }
        JSArray<?> other = (JSArray<?>) obj;
        return super.equals(other)
                && this.arrayDataGetter.equals(other.arrayDataGetter)
                && this.arrayDataSetter.equals(other.arrayDataSetter);
    }

    @Override
    public int hashCode()
    {
        return (super.hashCode() * 97 ^ arrayDataGetter.hashCode()) * 97 ^ arrayDataSetter.hashCode();
    }
}
