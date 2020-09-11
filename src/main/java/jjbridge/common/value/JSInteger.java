package jjbridge.common.value;

import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;

public class JSInteger extends JSPrimitive<Integer>
{
    public JSInteger(ValueGetter<Integer> getter, ValueSetter<Integer> setter)
    {
        super(getter, setter);
    }
}

