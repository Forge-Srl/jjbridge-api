package jjbridge.common.value.strategy;

import jjbridge.common.runtime.JSReference;

public interface ObjectPropertyGetter<R extends JSReference> {
    R getPropertyByName(String name);
}
