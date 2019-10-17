package jjbridge.common.value;

import jjbridge.common.value.strategy.ValueGetter;
import jjbridge.common.value.strategy.ValueSetter;

public class JSBoolean extends JSPrimitive<Boolean> {
    public JSBoolean(ValueGetter<Boolean> getter, ValueSetter<Boolean> setter) {
        super(getter, setter);
    }
}

