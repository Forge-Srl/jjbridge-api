package jjbridge.common.value.strategy;

import jjbridge.common.runtime.JSReference;

public interface ObjectPropertySetter<R extends JSReference> {
    void setPropertyByName(String name, R value);
}
