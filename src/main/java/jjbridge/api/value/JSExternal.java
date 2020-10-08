package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

/**
 * The External type of JavaScript.
 * <p>From the point of view of JavaScript code, an External value should be indistinguishable from an {@code Object}
 * value. Although the JavaScript engine does not expose this type to JavaScript code, this type allows storing
 * references to Java objects in JavaScript references for later access.</p>
 *
 * @param <T> the type of the Java object to store in the external reference.
 * */
public class JSExternal<T> implements JSValue
{
    private final ValueGetter<T> getter;
    private final ValueSetter<T> setter;

    public JSExternal(ValueGetter<T> getter, ValueSetter<T> setter)
    {
        this.getter = getter;
        this.setter = setter;
    }

    /**
     * Unwrap the Java value from the external value.
     *
     * @return the Java value stored in this external value
     * */
    public T getValue()
    {
        return getter.getValue();
    }

    /**
     * Wrap the Java value with this external value.
     *
     * @param value the Java value with which to update this external value
     * */
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
