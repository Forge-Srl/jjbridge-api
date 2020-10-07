package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

public interface FunctionSetter<R extends JSReference>
{
    void setFunction(FunctionCallback<R> callback);
}
