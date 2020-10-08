package jjbridge.api.value;

import jjbridge.api.runtime.JSReference;
import jjbridge.api.runtime.JSRuntime;
import jjbridge.api.runtime.TypeResolution;

/**
 * This class enumerates the standard JavaScript types.
 *
 * @see JSRuntime#newReference(JSType)
 * @see JSRuntime#resolveReference(JSReference, TypeResolution)
 * */
public enum JSType
{
    Undefined, Null, Boolean, Integer, Double, String, External, Object, Date, Array, Function
}
