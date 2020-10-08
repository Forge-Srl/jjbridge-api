package jjbridge.api.value;

import jjbridge.api.value.strategy.ValueGetter;
import jjbridge.api.value.strategy.ValueSetter;

/**
 * The {@code String} type of JavaScript.
 * */
public class JSString extends JSPrimitive<String>
{
    public JSString(ValueGetter<String> getter, ValueSetter<String> setter)
    {
        super(getter, setter);
    }
}
