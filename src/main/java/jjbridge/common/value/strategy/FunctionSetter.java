package jjbridge.common.value.strategy;

import jjbridge.common.runtime.JSReference;

public interface FunctionSetter<R extends JSReference>
{
    void setFunction(FunctionCallback<R> callback);
}
