package jjbridge.common.value;

import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;

public class JSExternal<T> implements JSValue
{
    private final ValueGetter<T> getter;
    private final ValueSetter<T> setter;

    public JSExternal(ValueGetter<T> getter, ValueSetter<T> setter)
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
        if (!(obj instanceof JSExternal))
        {
            return false;
        }
        JSExternal<?> other = (JSExternal<?>) obj;
        return this.getter.equals(other.getter) && this.setter.equals(other.setter);
    }

    @Override
    public int hashCode()
    {
        return getter.hashCode() * 97 ^ setter.hashCode();
    }
}
