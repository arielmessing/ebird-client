package arielmessing.ebird.api;

public interface ApiClient {
    <T> T getResource(String path, String token, Class<T> responseType);
}
