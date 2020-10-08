package jjbridge.api.value.strategy;

/**
 * Set a Java value to a JavaScript value.
 *
 * @param <T> the Java type of the value
 * */
public interface ValueSetter<T>
{
    /**
     * Converts a Java value to this Javascript value.
     *
     * @param value the Java value to convert
     * */
    void setValue(T value);
}
