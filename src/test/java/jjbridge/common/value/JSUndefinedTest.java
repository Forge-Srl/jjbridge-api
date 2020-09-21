package jjbridge.common.value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSUndefinedTest {
    @Test
    public void ToString() {
        JSUndefined undefined = new JSUndefined();
        assertEquals("undefined", undefined.toString());
    }
}
