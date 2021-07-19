package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

/**
 * The floating point number type of JavaScript.
 * */
public class JSFloat extends JSPrimitive<Double>
{
    public JSFloat(ValueGetter<Double> getter, ValueSetter<Double> setter)
    {
        super(getter, setter);
    }
}
