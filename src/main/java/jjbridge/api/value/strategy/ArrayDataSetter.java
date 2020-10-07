package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

public interface ArrayDataSetter<R extends JSReference>
{
    void setItemByPosition(int position, R value);
}
