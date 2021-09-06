package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

/**
 * The number type of JavaScript.
 * */
public class JSNumber extends JSPrimitive<Double>
{
    /**
     * Represents the maximum safe integer in JavaScript (2^53 - 1).
     */
    public static final long MAX_SAFE_INTEGER = (2L << 52) - 1;
    /**
     * Represents the minimum safe integer in JavaScript -(2^53 - 1).
     */
    public static final long MIN_SAFE_INTEGER = -1 * ((2L << 52) - 1);

    public static boolean isSafeJSInteger(long value)
    {
        return value <= MAX_SAFE_INTEGER && value >= MIN_SAFE_INTEGER;
    }

    public JSNumber(ValueGetter<Double> getter, ValueSetter<Double> setter)
    {
        super(getter, setter);
    }

    public Long getLongValue()
    {
        return getValue().longValue();
    }

    public void setLongValue(Long value)
    {
        setValue(value.doubleValue());
    }
}
