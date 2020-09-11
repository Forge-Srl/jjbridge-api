package jjbridge.common.value;

import jjbridge.common.runtime.JSReference;
import jjbridge.common.value.strategy.ObjectPropertyGetter;
import jjbridge.common.value.strategy.ObjectPropertySetter;

public class JSObject<R extends JSReference> implements JSValue
{
    private final ObjectPropertyGetter<R> propertyGetter;
    private final ObjectPropertySetter<R> propertySetter;

    public JSObject(ObjectPropertyGetter<R> propertyGetter, ObjectPropertySetter<R> propertySetter)
    {
        this.propertyGetter = propertyGetter;
        this.propertySetter = propertySetter;
    }

    public JSReference get(String name)
    {
        return this.propertyGetter.getPropertyByName(name);
    }

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
        @SuppressWarnings("rawtypes")
        JSObject other = (JSObject) obj;
        return this.propertyGetter.equals(other.propertyGetter) && this.propertySetter.equals(other.propertySetter);
    }

    @Override
    public int hashCode()
    {
        return propertyGetter.hashCode() * 97 ^ propertySetter.hashCode();
    }
}
