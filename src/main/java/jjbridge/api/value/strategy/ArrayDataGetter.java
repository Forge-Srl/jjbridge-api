package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

public interface ArrayDataGetter<R extends JSReference>
{
    int getSize();

    R getItemByPosition(int position);
}
