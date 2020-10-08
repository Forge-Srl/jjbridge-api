package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.value.strategy.ObjectPropertyGetter;
import jjbridge.api.value.strategy.ObjectPropertySetter;
import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

import java.util.Date;

/**
 * The {@code Date} type of JavaScript.
 * */
public class JSDate<R extends JSReference> extends JSObject<R>
{
    private final ValueGetter<Date> getter;
    private final ValueSetter<Date> setter;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public JSDate(ValueGetter<Date> getter, ValueSetter<Date> setter, ObjectPropertyGetter<R> propertyGetter,
                  ObjectPropertySetter<R> propertySetter)
    {
        super(propertyGetter, propertySetter);
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * Extract the Java {@link Date} value from the JavaScript one.
     *
     * @return the Java value corresponding to this JavaScript date
     * */
    public Date getValue()
    {
        return getter.getValue();
    }

    /**
     * Update the JavaScript date value with the Java one.
     *
     * @param value the Java date with which to update this JavaScript value
     * */
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
