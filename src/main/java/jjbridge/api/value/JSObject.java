package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.value.strategy.ObjectPropertyGetter;
import jjbridge.api.value.strategy.ObjectPropertySetter;

/**
 * This class is the root of all JavaScript Object types.
 * */
public class JSObject<R extends JSReference> implements JSValue
{
    private final ObjectPropertyGetter<R> propertyGetter;
    private final ObjectPropertySetter<R> propertySetter;

    public JSObject(ObjectPropertyGetter<R> propertyGetter, ObjectPropertySetter<R> propertySetter)
    {
        this.propertyGetter = propertyGetter;
        this.propertySetter = propertySetter;
    }

    /**
     * Provides a reference to a property of this JavaScript object.
     *
     * @param name the name of the property to access
     * @return the reference to the property
     * */
    public JSReference get(String name)
    {
        return this.propertyGetter.getPropertyByName(name);
    }

    /**
     * Assign the given reference to a property of this JavaScript object.
     *
     * @param name the name of the property to access
     * @param reference the new reference pointed by the property
     * */
    @SuppressWarnings("unchecked")
    public void set(String name, JSReference reference)
    {
        this.propertySetter.setPropertyByName(name, (R) reference);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof JSObject))
        {
            return false;
        }
        JSObject<?> other = (JSObject<?>) obj;
        return this.propertyGetter.equals(other.propertyGetter) && this.propertySetter.equals(other.propertySetter);
    }

    @Override
    public int hashCode()
    {
        return propertyGetter.hashCode() * 97 ^ propertySetter.hashCode();
    }
}
