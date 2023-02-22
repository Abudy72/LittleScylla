package Logic.Exceptions;
public class ServerNotSupported extends RuntimeException {
    public ServerNotSupported(String message) {
        super(message);
    }

    public ServerNotSupported(Throwable cause) {
        super(cause);
    }

    public ServerNotSupported(String message, Throwable cause) {
        super(message, cause);
    }
}
