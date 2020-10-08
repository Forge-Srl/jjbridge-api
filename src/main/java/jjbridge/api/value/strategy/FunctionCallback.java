package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

/**
 * This interface represents the general definition of a JavaScript function.
 * */
public interface FunctionCallback<R extends JSReference>
{
    /**
     * A JavaScript function takes references to JavaScript values and returns a reference to a JavaScript value.
     *
     * @param arguments the parameters of the function
     * @return the result of the function
     * */
    @SuppressWarnings("unchecked")
    R apply(R... arguments);
}
