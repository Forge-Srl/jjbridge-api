package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

/**
 * Set the reference of a JavaScript object property.
 * */
public interface ObjectPropertySetter<R extends JSReference>
{
    /**
     * Change the reference to the given property of this JavaScript object.
     *
     * @param name the name of the property
     * @param value the new reference pointed by the property
     * */
    void setPropertyByName(String name, R value);
}
