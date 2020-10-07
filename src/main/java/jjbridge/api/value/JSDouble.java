package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

public class JSDouble extends JSPrimitive<Double>
{
    public JSDouble(ValueGetter<Double> getter, ValueSetter<Double> setter)
    {
        super(getter, setter);
    }
}
