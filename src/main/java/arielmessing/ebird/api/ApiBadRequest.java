package arielmessing.ebird.api;

public record ApiBadRequest(Error[] errors) {

    public record Error(String status, String code, String title) {}
}