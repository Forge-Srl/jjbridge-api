package jjbridge.common.runtime;

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
