package Exceptions;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(String message) {
        super(message);
    }

    public PlayerNotFoundException(Throwable cause) {
        super(cause);
    }

    public PlayerNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
