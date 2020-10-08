package jjbridge.api.runtime;

/**
 * This exception represents an error occurred while the JavaScript engine was running a script. The exception message
 * contains the JavaScript stack trace together with additional Java stack trace when available.
 * */
public class ExecutionException extends RuntimeException
{
    public ExecutionException(String message)
    {
        super(format(message));
    }

    public ExecutionException(String message, Throwable innerException)
    {
        super(format(message), innerException);
    }

    private static String format(String errorMessage)
    {
        return errorMessage.replace("\n", "\n\t|JS|");
    }
}
