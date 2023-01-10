package Exceptions;

public class SmiteAPIUnavailableException extends RuntimeException{
    public SmiteAPIUnavailableException(String message) {
        super(message);
    }

    public SmiteAPIUnavailableException(Throwable cause) {
        super(cause);
    }

    public SmiteAPIUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
