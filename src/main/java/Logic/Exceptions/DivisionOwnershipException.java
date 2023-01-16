package Logic.Exceptions;

public class DivisionOwnershipException extends Exception{
    public DivisionOwnershipException(String message) {
        super(message);
    }

    public DivisionOwnershipException(Throwable cause) {
        super(cause);
    }

    public DivisionOwnershipException(String message, Throwable cause) {
        super(message, cause);
    }
}
