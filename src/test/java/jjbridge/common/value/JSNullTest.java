package jjbridge.common.value;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class JSNullTest {
    @Test
    public void ToString() {
        JSNull jsNull = new JSNull();
        assertEquals("null", jsNull.toString());
    }
}
