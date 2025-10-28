package arielmessing.ebird.client;

import arielmessing.ebird.exceptions.EbirdBadRequestException;
import arielmessing.ebird.exceptions.EbirdException;
import arielmessing.ebird.exceptions.EbirdForbiddenException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.util.List;

import static arielmessing.ebird.client.EbirdApiHttpClient.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EbirdApiHttpClientTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private HttpResponse<String> mockResponse;

    private EbirdApiHttpClient client;

    @BeforeEach
    void setUp() {
        client = new EbirdApiHttpClient("https://api.ebird.org/v2/", mockHttpClient, new ObjectMapper());
    }

    @Test
    void getResource_handleOkRequest() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        when(mockResponse.statusCode()).thenReturn(STATUS_OK);
        when(mockResponse.body()).thenReturn("\"result\"");

        String result = client.getResource("path/to/resource", "token", String.class);

        assertEquals("result", result);
    }

    @Test
    void getResourceAsListOf_handleOkRequest() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        when(mockResponse.statusCode()).thenReturn(STATUS_OK);
        when(mockResponse.body()).thenReturn("[ \"result\" ]");

        List<String> result = client.getResourceAsListOf("path/to/resource", "token", String.class);

        assertEquals("result", result.getFirst());
    }

    @Test
    void getResource_handleBadRequest() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        when(mockResponse.statusCode()).thenReturn(STATUS_BAD_REQUEST);
        when(mockResponse.body()).thenReturn("{ \"errors\": [ { \"status\": \"Status\", \"code\": \"Code\", \"title\": \"Title\" } ] }");

        EbirdBadRequestException ex = assertThrows(
                EbirdBadRequestException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );

        assertEquals("Request failed with error(s)", ex.getMessage());
        assertNotNull(ex.badRequest);
        assertEquals(1, ex.badRequest.errors().size());
    }

    @Test
    void getResource_handleForbidden() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        when(mockResponse.statusCode()).thenReturn(STATUS_FORBIDDEN);

        EbirdForbiddenException ex = assertThrows(
                EbirdForbiddenException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );
    }

    @Test
    void getResource_handleServerError() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        var statusCode = 500;
        when(mockResponse.statusCode()).thenReturn(statusCode);

        EbirdException ex = assertThrows(
                EbirdException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );

        assertEquals("Request failed with status: " + statusCode, ex.getMessage());
    }

    @Test
    void getResource_handleJsonProcessingException() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        when(mockResponse.statusCode()).thenReturn(STATUS_OK);
        when(mockResponse.body()).thenReturn("Something which is not a valid JSON");

        EbirdException ex = assertThrows(
                EbirdException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );

        assertEquals("Error parsing response", ex.getMessage());
        assertInstanceOf(IOException.class, ex.getCause());
    }

    @Test
    void getResource_handleIOException() throws Exception {
        when(mockHttpClient.send(any(), any())).thenThrow(new IOException());

        EbirdException ex = assertThrows(
                EbirdException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );

        assertEquals("Error during API call", ex.getMessage());
        assertInstanceOf(IOException.class, ex.getCause());
    }
}