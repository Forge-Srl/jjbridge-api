package jjbridge.api.runtime;

import jjbridge.api.value.JSType;

/**
 * This interface represents a reference to JavaScript value inside the engine memory.
 * An instance of this class should be considered like a pointer to a JavaScript value.
 * */
public interface JSReference
{
    /**
     * Provides the nominal type of the value pointed by this reference.
     *
     * @return the nominal type of the reference
     * @see JSRuntime#resolveReference(JSReference, TypeResolution)
     * */
    JSType getNominalType();

    /**
     * Provides the actual type of the value pointed by this reference.
     *
     * @return the actual type of the reference
     * @see JSRuntime#resolveReference(JSReference, TypeResolution)
     * */
    JSType getActualType();
}
