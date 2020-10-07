package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

public abstract class JSPrimitive<T> implements JSValue
{
    private final ValueGetter<T> getter;
    private final ValueSetter<T> setter;

    protected JSPrimitive(ValueGetter<T> getter, ValueSetter<T> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }

    public T getValue()
    {
        return getter.getValue();
    }

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
