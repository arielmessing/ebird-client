package arielmessing.ebird.client;

public class EbirdClientException extends RuntimeException {

    public EbirdClientException(String message) {
        super(message);
    }

    public EbirdClientException(String message, Throwable cause) {
        super(message, cause);
    }
}
