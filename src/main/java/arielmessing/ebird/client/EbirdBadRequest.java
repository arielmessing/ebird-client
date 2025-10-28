package arielmessing.ebird.client;

public record EbirdBadRequest(Error[] errors) {

    public record Error(String status, String code, String title) {}
}