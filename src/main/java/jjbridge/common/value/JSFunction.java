package jjbridge.common.value;

import jjbridge.common.runtime.JSReference;
import jjbridge.common.value.strategy.*;

import java.lang.reflect.Array;
import java.util.Arrays;

public class JSFunction<R extends JSReference> extends JSObject<R> {
    private final Class<R> jsReferenceType;
    private final FunctionInvoker<R> functionInvoker;
    private final FunctionSetter<R> functionSetter;

    public JSFunction(Class<R> type, ObjectPropertyGetter<R> propertyGetter, ObjectPropertySetter<R> propertySetter,
                      FunctionInvoker<R> functionInvoker, FunctionSetter<R> functionSetter) {
        super(propertyGetter, propertySetter);
        this.jsReferenceType = type;
        this.functionInvoker = functionInvoker;
        this.functionSetter = functionSetter;
    }

    @SuppressWarnings("unchecked")
    public JSReference invoke(JSReference receiver, JSReference... args) {
        return this.functionInvoker.invokeFunction((R) receiver, castArray(args));
    }

    public JSReference invokeConstructor(JSReference... args) {
        return this.functionInvoker.invokeConstructor(castArray(args));
    }

    @SuppressWarnings("unchecked")
    private R[] castArray(JSReference[] original) {
        R[] casted = (R[]) Array.newInstance(jsReferenceType, original.length);
        casted = Arrays.asList(original).toArray(casted);
        return casted;
    }

    @SuppressWarnings("unchecked")
    public void setFunction(@SuppressWarnings("rawtypes") FunctionCallback callback) {
        this.functionSetter.setFunction((FunctionCallback<R>) callback);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof JSFunction)) return false;
        @SuppressWarnings("rawtypes")
        JSFunction other = (JSFunction) obj;
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
