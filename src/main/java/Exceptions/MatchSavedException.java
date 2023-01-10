package Exceptions;

public class MatchSavedException extends Exception{
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
