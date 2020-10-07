package jjbridge.api.value;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JSNullTest {
    @Test
    public void ToString() {
        JSNull jsNull = new JSNull();
        assertEquals("null", jsNull.toString());
    }
}
