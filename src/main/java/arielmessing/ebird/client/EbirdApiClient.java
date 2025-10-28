package arielmessing.ebird.client;

import java.util.List;

public interface EbirdApiClient {

    <T> T getResource(String resourcePath, String token, Class<T> responseType);

    <T> List<T> getResourceAsListOf(String resourcePath, String token, Class<T> responseType);
}
