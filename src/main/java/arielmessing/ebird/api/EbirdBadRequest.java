package arielmessing.ebird.api;

import java.util.List;

public record EbirdBadRequest(List<Error> errors) {

    public EbirdBadRequest {
        errors = List.copyOf(errors);
    }

    public record Error(String status, String code, String title) {}
}