package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

/**
 * The {@code Integer} type of JavaScript.
 * */
public class JSInteger extends JSPrimitive<Integer>
{
    public JSInteger(ValueGetter<Integer> getter, ValueSetter<Integer> setter)
    {
        super(getter, setter);
    }
}

