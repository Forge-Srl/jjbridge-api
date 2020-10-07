package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

public interface FunctionCallback<R extends JSReference>
{
    R apply(R... arguments);
}
