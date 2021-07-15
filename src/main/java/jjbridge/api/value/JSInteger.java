package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

/**
 * The {@code Integer} number type of JavaScript.
 * */
public class JSInteger extends JSPrimitive<Long>
{
    public JSInteger(ValueGetter<Long> getter, ValueSetter<Long> setter)
    {
        super(getter, setter);
    }
}

