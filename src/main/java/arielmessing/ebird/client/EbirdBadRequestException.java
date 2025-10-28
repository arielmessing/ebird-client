package arielmessing.ebird.client;

public class EbirdBadRequestException extends EbirdClientException {

    public final EbirdBadRequest badRequest;

    public EbirdBadRequestException(String message, EbirdBadRequest badRequest) {
        super(message);

        this.badRequest = badRequest;
    }
}
