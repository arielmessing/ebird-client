package arielmessing.ebird.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public final class ApiHttpClient implements ApiClient {

    private final String baseUrl;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ApiHttpClient(HttpClient httpClient, ObjectMapper objectMapper) {
        this.baseUrl = System.getenv("API_URL");

        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T getResource(String resourcePath, String token, Class<T> responseType) throws ApiException {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(baseUrl + resourcePath))
                    .header("Accept", "application/json")
                    .header("X-eBirdApiToken", token)
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return objectMapper.readValue(response.body(), responseType);

            } else {
                throw new ApiException("Request failed with status: " + response.statusCode());
            }
        } catch (JsonProcessingException e) {
            throw new ApiException("Error parsing response", e);

        } catch (IOException | InterruptedException e) {
            throw new ApiException("Error during API call", e);
        }
    }
}
