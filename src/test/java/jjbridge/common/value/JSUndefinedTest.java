package jjbridge.common.value;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSUndefinedTest {
    @Test
    public void ToString() {
        JSUndefined undefined = new JSUndefined();
        assertEquals("undefined", undefined.toString());
    }
}
