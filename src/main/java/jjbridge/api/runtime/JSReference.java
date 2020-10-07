package jjbridge.api.runtime;

import jjbridge.api.value.JSType;

public interface JSReference
{
    JSType getNominalType();

    JSType getActualType();
}
