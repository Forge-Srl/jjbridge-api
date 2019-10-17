package jjbridge.common.value.strategy;

import jjbridge.common.runtime.JSReference;

public interface FunctionInvoker<R extends JSReference> {
    R invokeFunction(R receiver, R[] args);

    R invokeConstructor(R[] args);
}
