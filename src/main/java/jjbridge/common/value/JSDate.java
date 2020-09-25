package jjbridge.common.value;

import jjbridge.common.runtime.JSReference;
import jjbridge.common.value.strategy.ObjectPropertyGetter;
import jjbridge.common.value.strategy.ObjectPropertySetter;
import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;

import java.util.Date;

public class JSDate<R extends JSReference> extends JSObject<R>
{
    private final ValueGetter<Date> getter;
    private final ValueSetter<Date> setter;

    public JSDate(ValueGetter<Date> getter, ValueSetter<Date> setter, ObjectPropertyGetter<R> propertyGetter,
                  ObjectPropertySetter<R> propertySetter)
    {
        super(propertyGetter, propertySetter);
        this.getter = getter;
        this.setter = setter;
    }

    public Date getValue()
    {
        return getter.getValue();
    }

    public void setValue(Date value)
    {
        setter.setValue(value);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof JSDate))
        {
            return false;
        }
        JSDate<?> other = (JSDate<?>) obj;
        return super.equals(other) && this.getter.equals(other.getter) && this.setter.equals(other.setter);
    }

    @Override
    public int hashCode()
    {
        return (super.hashCode() * 97 ^ getter.hashCode()) * 97 ^ setter.hashCode();
    }
}
