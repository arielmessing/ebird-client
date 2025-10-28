package arielmessing.ebird.exceptions;

public class EbirdException extends RuntimeException {

    public EbirdException(String message) {
        super(message);
    }

    public EbirdException(String message, Throwable cause) {
        super(message, cause);
    }
}
