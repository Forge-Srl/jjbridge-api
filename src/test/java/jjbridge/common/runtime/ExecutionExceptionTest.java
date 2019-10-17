package jjbridge.common.runtime;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ExecutionExceptionTest {
    @Test
    public void Ctor() {
        String message = "message";
        ExecutionException exception = new ExecutionException(message);
        assertEquals(message, exception.getMessage());
    }
}
