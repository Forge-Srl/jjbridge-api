package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

public interface ObjectPropertyGetter<R extends JSReference>
{
    R getPropertyByName(String name);
}
