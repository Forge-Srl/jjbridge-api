package jjbridge.common.value.strategy;

import jjbridge.common.runtime.JSReference;

public interface ArrayDataGetter<R extends JSReference>
{
    int getSize();

    R getItemByPosition(int position);
}
