package arielmessing.ebird.exceptions;

import arielmessing.ebird.api.EbirdBadRequest;

public class EbirdBadRequestException extends EbirdException {

    public final EbirdBadRequest badRequest;

    public EbirdBadRequestException(String message, EbirdBadRequest badRequest) {
        super(message);

        this.badRequest = badRequest;
    }
}
