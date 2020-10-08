package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

/**
 * Set the code of the JavaScript function.
 * */
public interface FunctionSetter<R extends JSReference>
{
    /**
     * Change the code of this JavaScript function.
     *
     * @param callback the new code to be invoked by the function
     * */
    void setFunction(FunctionCallback<R> callback);
}
