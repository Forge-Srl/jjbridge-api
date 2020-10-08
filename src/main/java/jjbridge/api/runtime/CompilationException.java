package jjbridge.api.runtime;

/**
 * This exception represents an error occurred before the JavaScript engine can run a script. This typically means there
 * is a syntax error in the JavaScript code provided to the runtime.
 * */
public class CompilationException extends RuntimeException
{
    public CompilationException(String message)
    {
        super(message);
    }
}
