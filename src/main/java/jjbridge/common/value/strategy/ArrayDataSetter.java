package jjbridge.common.value.strategy;

import jjbridge.common.runtime.JSReference;

public interface ArrayDataSetter<R extends JSReference> {
    void setItemByPosition(int position, R value);
}
