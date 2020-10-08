package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

/**
 * Get the reference to a JavaScript object property.
 * */
public interface ObjectPropertyGetter<R extends JSReference>
{
    /**
     * Provides the reference to the given property of this JavaScript object.
     *
     * @param name the name of the property
     * @return the reference to the property
     * */
    R getPropertyByName(String name);
}
