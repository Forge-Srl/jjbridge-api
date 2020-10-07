package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

public class JSBoolean extends JSPrimitive<Boolean>
{
    public JSBoolean(ValueGetter<Boolean> getter, ValueSetter<Boolean> setter)
    {
        super(getter, setter);
    }
}

