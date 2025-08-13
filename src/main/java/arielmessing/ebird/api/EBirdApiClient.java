package arielmessing.ebird.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class EBirdApiClient {

    private static final String BASE_URL = "https://api.ebird.org/v2/";

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public EBirdApiClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    public <T> T getResource(String resourcePath, String token, Class<T> responseType) throws EBirdApiException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + resourcePath))
                    .header("Accept", "application/json")
                    .header("X-eBirdApiToken", token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), responseType);

            } else {
                throw new EBirdApiException("Request failed with status: " + response.statusCode());
            }
        } catch (JsonProcessingException e) {
            throw new EBirdApiException("Error parsing response", e);

        } catch (IOException | InterruptedException e) {
            throw new EBirdApiException("Error during API call", e);
        }
    }
}
