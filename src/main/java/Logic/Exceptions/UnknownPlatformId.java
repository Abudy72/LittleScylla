package Logic.Exceptions;

public class UnknownPlatformId extends Exception{
    public UnknownPlatformId(String message) {
        super(message);
    }

    public UnknownPlatformId(Throwable cause) {
        super(cause);
    }

    public UnknownPlatformId(String message, Throwable cause) {
        super(message, cause);
    }
}
