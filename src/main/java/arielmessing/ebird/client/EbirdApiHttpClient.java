package arielmessing.ebird.client;

import arielmessing.ebird.api.EbirdBadRequest;
import arielmessing.ebird.exceptions.EbirdBadRequestException;
import arielmessing.ebird.exceptions.EbirdException;
import arielmessing.ebird.exceptions.EbirdForbiddenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public final class EbirdApiHttpClient implements EbirdApiClient {

    public static final String DEFAULT_BASE_URL = "https://api.ebird.org/v2/";
    public static final String BASE_URL_PROPERTY = "api.base.url";

    static final int STATUS_OK = 200;
    static final int STATUS_BAD_REQUEST = 400;
    static final int STATUS_FORBIDDEN = 403;

    private final String baseUrl;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public EbirdApiHttpClient() {
        this(System.getProperty(BASE_URL_PROPERTY, DEFAULT_BASE_URL), HttpClient.newBuilder().build(), new ObjectMapper());
    }

    public EbirdApiHttpClient(String baseUrl, HttpClient httpClient, ObjectMapper objectMapper) {
        this.baseUrl = baseUrl;

        this.httpClient = httpClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public <T> T getResource(String resourcePath, String token, Class<T> responseType) {
        var type = objectMapper.getTypeFactory().constructType(responseType);

        return getResource(resourcePath, token, type);
    }

    @Override
    public <T> List<T> getResourceAsListOf(String resourcePath, String token, Class<T> elementType) {
        var type = objectMapper.getTypeFactory().constructCollectionType(List.class, elementType);

        return getResource(resourcePath, token, type);
    }

    <T> T getResource(String resourcePath, String token, JavaType responseType) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .GET()
                    .uri(URI.create(baseUrl + resourcePath))
                    .header("Accept", "application/json")
                    .header("X-eBirdApiToken", token)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == STATUS_OK) {
                return objectMapper.readValue(response.body(), responseType);

            } else if (response.statusCode() == STATUS_BAD_REQUEST) {

                EbirdBadRequest badRequest =
                        objectMapper.readValue(response.body(), EbirdBadRequest.class);

                throw new EbirdBadRequestException("Request failed with error(s)", badRequest);

            } else if (response.statusCode() == STATUS_FORBIDDEN) {
                throw new EbirdForbiddenException();

            } else {
                throw new EbirdException("Request failed with status: " + response.statusCode());
            }
        } catch (JsonProcessingException e) {
            throw new EbirdException("Error parsing response", e);

        } catch (IOException | InterruptedException e) {
            throw new EbirdException("Error during API call", e);
        }
    }
}
