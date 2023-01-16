package Logic.Exceptions;

public class MatchUnavailableException extends RuntimeException{
    public MatchUnavailableException(String message) {
        super(message);
    }

    public MatchUnavailableException(Throwable cause) {
        super(cause);
    }

    public MatchUnavailableException(String message, Throwable cause) {
        super(message, cause);
    }
}
