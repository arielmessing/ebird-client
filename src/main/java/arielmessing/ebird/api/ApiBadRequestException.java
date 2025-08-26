package arielmessing.ebird.api;

public class ApiBadRequestException extends ApiException {

    public final ApiBadRequest badRequest;

    public ApiBadRequestException(String message, ApiBadRequest badRequest) {
        super(message);

        this.badRequest = badRequest;
    }
}
