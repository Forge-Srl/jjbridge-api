package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

/**
 * Defines invocation methods for a JavaScript function.
 * */
public interface FunctionInvoker<R extends JSReference>
{
    /**
     * Invokes this JavaScript function with the given receiver and arguments.
     *
     * @param receiver the reference to the receiver of the function
     * @param args the references to the arguments of the function
     * @return the reference to the result of the invocation
     * */
    R invokeFunction(R receiver, R[] args);

    /**
     * Invokes this JavaScript function as a constructor with the given arguments.
     *
     * @param args the references to the arguments of the function
     * @return the reference to the result of the invocation
     * */
    R invokeConstructor(R[] args);
}
