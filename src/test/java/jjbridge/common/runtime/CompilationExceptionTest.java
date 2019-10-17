package jjbridge.common.runtime;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CompilationExceptionTest {
    @Test
    public void Ctor() {
        String message = "message";
        CompilationException exception = new CompilationException(message);
        assertEquals(message, exception.getMessage());
    }
}
