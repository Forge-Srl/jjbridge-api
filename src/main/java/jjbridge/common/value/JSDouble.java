package jjbridge.common.value;

import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;

public class JSDouble extends JSPrimitive<Double>
{
    public JSDouble(ValueGetter<Double> getter, ValueSetter<Double> setter)
    {
        super(getter, setter);
    }
}
