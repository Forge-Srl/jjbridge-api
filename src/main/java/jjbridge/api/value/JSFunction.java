package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.value.strategy.FunctionCallback;
import jjbridge.api.value.strategy.FunctionInvoker;
import jjbridge.api.value.strategy.FunctionSetter;
import jjbridge.api.value.strategy.ObjectPropertyGetter;
import jjbridge.api.value.strategy.ObjectPropertySetter;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * The {@code Function} type of JavaScript.
 * */
public class JSFunction<R extends JSReference> extends JSObject<R>
{
    private final Class<R> jsReferenceType;
    private final FunctionInvoker<R> functionInvoker;
    private final FunctionSetter<R> functionSetter;

    @SuppressWarnings("checkstyle:MissingJavadocMethod")
    public JSFunction(Class<R> type, ObjectPropertyGetter<R> propertyGetter, ObjectPropertySetter<R> propertySetter,
                      FunctionInvoker<R> functionInvoker, FunctionSetter<R> functionSetter)
    {
        super(propertyGetter, propertySetter);
        this.jsReferenceType = type;
        this.functionInvoker = functionInvoker;
        this.functionSetter = functionSetter;
    }

    /**
     * Calls this JavaScript function on a receiver with the given parameters.
     *
     * @param receiver the receiver of this function invocation. In JavaScript code the receiver is the value pointed by
     *                 {@code this} inside the function body.
     * @param args the arguments of this function invocation
     * @return a reference to the value returned by this JavaScript function
     * */
    @SuppressWarnings("unchecked")
    public JSReference invoke(JSReference receiver, JSReference... args)
    {
        return this.functionInvoker.invokeFunction((R) receiver, castArray(args));
    }

    /**
     * Calls this JavaScript function as an object constructor with the given parameters.
     *
     * @param args the arguments of this function invocation
     * @return a reference to the object constructed by this JavaScript function
     * */
    public JSReference invokeConstructor(JSReference... args)
    {
        return this.functionInvoker.invokeConstructor(castArray(args));
    }

    @SuppressWarnings("unchecked")
    private R[] castArray(JSReference[] original)
    {
        R[] casted = (R[]) Array.newInstance(jsReferenceType, original.length);
        casted = Arrays.asList(original).toArray(casted);
        return casted;
    }

    /**
     * Changes the actual code to be executed when invoking this JavaScript function.
     *
     * @param callback the new code of this JavaScript function
     * */
    @SuppressWarnings("unchecked")
    public void setFunction(FunctionCallback<?> callback)
    {
        this.functionSetter.setFunction((FunctionCallback<R>) callback);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof JSFunction))
        {
            return false;
        }
        JSFunction<?> other = (JSFunction<?>) obj;
        return super.equals(other)
                && this.functionInvoker.equals(other.functionInvoker)
                && this.functionSetter.equals(other.functionSetter);
    }

    @Override
    public int hashCode()
    {
        return (super.hashCode() * 97 ^ functionInvoker.hashCode()) * 97 ^ functionSetter.hashCode();
    }
}
