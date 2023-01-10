package Exceptions;

public class MatchSavedException extends RuntimeException{
    public MatchSavedException(String message) {
        super(message);
    }

    public MatchSavedException(Throwable cause) {
        super(cause);
    }

    public MatchSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
