package jjbridge.api.value.strategy;

/**
 * Get the Java value from a JavaScript value.
 *
 * @param <T> the resulting Java type for the conversion
 * */
public interface ValueGetter<T>
{
    /**
     * Converts this Javascript value to the Java value.
     *
     * @return the Java value for this JavaScript value
     * */
    T getValue();
}
