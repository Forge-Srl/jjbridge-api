package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

/**
 * This class is the root of all JavaScript primitive types.
 *
 * @param <T> the Java type corresponding to the JavaScript type
 * */
public abstract class JSPrimitive<T> implements JSValue
{
    private final ValueGetter<T> getter;
    private final ValueSetter<T> setter;

    protected JSPrimitive(ValueGetter<T> getter, ValueSetter<T> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * Extract the Java value from the JavaScript one.
     *
     * @return the Java value corresponding to this JavaScript value
     * */
    public T getValue()
    {
        return getter.getValue();
    }

    /**
     * Update the JavaScript value with the Java one.
     *
     * @param value the Java value with which to update this JavaScript value
     * */
    public void setValue(T value)
    {
        setter.setValue(value);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof JSPrimitive))
        {
            return false;
        }
        JSPrimitive<?> other = (JSPrimitive<?>) obj;
        return this.getter.equals(other.getter) && this.setter.equals(other.setter);
    }

    @Override
    public int hashCode()
    {
        return getter.hashCode() * 97 ^ setter.hashCode();
    }
}
