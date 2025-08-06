package arielmessing.ebird.api;

public class EBirdApiException extends Exception {
    public EBirdApiException(String message) {
        super(message);
    }

    public EBirdApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
