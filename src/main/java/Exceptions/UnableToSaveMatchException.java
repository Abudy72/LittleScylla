package Exceptions;

public class UnableToSaveMatchException extends RuntimeException{

    public UnableToSaveMatchException(String message) {
        super(message);
    }

    public UnableToSaveMatchException(Throwable cause) {
        super(cause);
    }

    public UnableToSaveMatchException(String message, Throwable cause) {
        super(message, cause);
    }
}
