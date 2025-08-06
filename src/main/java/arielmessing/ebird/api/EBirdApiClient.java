package arielmessing.ebird.api;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class EBirdApiClient {

    private static final String BASE_URL = "https://api.ebird.org/v2/";

    private final String token;
    private final HttpClient httpClient;

    public EBirdApiClient(String token, HttpClient httpClient) {
        this.token = token;
        this.httpClient = httpClient;
    }

    public String getResource(String resourcePath) throws EBirdApiException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + resourcePath))
                    .header("Accept", "application/json")
                    .header("X-eBirdApiToken", token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return response.body();

            } else {
                throw new EBirdApiException("Request failed with status: " + response.statusCode());
            }

        } catch (IOException | InterruptedException e) {
            throw new EBirdApiException("Error during API call", e);
        }
    }
}
