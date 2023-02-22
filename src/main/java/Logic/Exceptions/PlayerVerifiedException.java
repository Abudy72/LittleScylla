package Logic.Exceptions;

public class PlayerVerifiedException extends Exception{
    public PlayerVerifiedException(String message) {
        super(message);
    }

    public PlayerVerifiedException(Throwable cause) {
        super(cause);
    }

    public PlayerVerifiedException(String message, Throwable cause) {
        super(message, cause);
    }
}
