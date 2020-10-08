package jjbridge.api.value.strategy;

import jjbridge.api.runtime.JSReference;

/**
 * Get information and items from a JavaScript array.
 * */
public interface ArrayDataGetter<R extends JSReference>
{
    /**
     * Provides the size of this JavaScript array.
     *
     * @return the length of the array
     * */
    int getSize();

    /**
     * Provides the reference to the given item of this JavaScript array.
     *
     * @param position the position of the item
     * @return the reference to the item
     * */
    R getItemByPosition(int position);
}
