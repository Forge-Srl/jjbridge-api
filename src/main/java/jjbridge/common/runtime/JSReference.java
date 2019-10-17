package jjbridge.common.runtime;

import jjbridge.common.value.JSType;

public interface JSReference {
    JSType getNominalType();

    JSType getActualType();
}
