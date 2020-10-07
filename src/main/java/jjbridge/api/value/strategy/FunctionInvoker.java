package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

public interface FunctionInvoker<R extends JSReference>
{
    R invokeFunction(R receiver, R[] args);

    R invokeConstructor(R[] args);
}
