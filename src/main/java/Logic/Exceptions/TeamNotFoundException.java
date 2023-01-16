package Logic.Exceptions;

public class TeamNotFoundException extends Exception{
    public TeamNotFoundException(String message) {
        super(message);
    }

    public TeamNotFoundException(Throwable cause) {
        super(cause);
    }

    public TeamNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
