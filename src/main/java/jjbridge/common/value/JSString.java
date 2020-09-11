package jjbridge.common.value;

import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;

public class JSString extends JSPrimitive<String>
{
    public JSString(ValueGetter<String> getter, ValueSetter<String> setter)
    {
        super(getter, setter);
    }
}
