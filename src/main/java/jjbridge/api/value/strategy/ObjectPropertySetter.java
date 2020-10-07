package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

public interface ObjectPropertySetter<R extends JSReference>
{
    void setPropertyByName(String name, R value);
}
