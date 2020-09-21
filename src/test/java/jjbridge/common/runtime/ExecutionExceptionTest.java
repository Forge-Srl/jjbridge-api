package jjbridge.common.runtime;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ExecutionExceptionTest {
    @Test
    public void Ctor() {
        String message = "message";
        ExecutionException exception = new ExecutionException(message);
        assertEquals(message, exception.getMessage());
    }
}
