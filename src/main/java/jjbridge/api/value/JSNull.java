package jjbridge.api.value;

/**
 * The {@code null} type of JavaScript.
 * */
public class JSNull implements JSValue
{
    @Override
    public String toString()
    {
        return "null";
    }
}
