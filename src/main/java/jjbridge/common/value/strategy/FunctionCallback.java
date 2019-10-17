package jjbridge.common.value.strategy;

import jjbridge.common.runtime.JSReference;

public interface FunctionCallback<R extends JSReference> {
    R apply(R... arguments);
}
