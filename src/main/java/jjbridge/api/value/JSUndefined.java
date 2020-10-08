package jjbridge.api.value;

/**
 * The {@code undefined} type of JavaScript.
 * */
public class JSUndefined implements JSValue
{
    @Override
    public String toString()
    {
        return "undefined";
    }
}
