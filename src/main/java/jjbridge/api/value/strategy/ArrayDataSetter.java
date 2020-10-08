package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

/**
 * Set the reference of an item of the JavaScript array.
 * */
public interface ArrayDataSetter<R extends JSReference>
{
    /**
     * Change the reference to the given item of this JavaScript array.
     *
     * @param position the position of the item
     * @param value the new reference pointed by the item
     * */
    void setItemByPosition(int position, R value);
}
