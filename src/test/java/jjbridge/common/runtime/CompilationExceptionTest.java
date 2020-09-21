package jjbridge.common.runtime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilationExceptionTest {
    @Test
    public void Ctor() {
        String message = "message";
        CompilationException exception = new CompilationException(message);
        assertEquals(message, exception.getMessage());
    }
}
