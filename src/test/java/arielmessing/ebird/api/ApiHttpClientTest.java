package arielmessing.ebird.api;

import arielmessing.ebird.api.regions.RegionInfo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
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

import static arielmessing.ebird.api.ApiHttpClient.STATUS_BAD_REQUEST;
import static arielmessing.ebird.api.ApiHttpClient.STATUS_OK;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ApiHttpClientTest {

    @Mock
    private HttpClient mockHttpClient;

    @Mock
    private ObjectMapper mockObjectMapper;

    @Mock
    private HttpResponse<String> mockResponse;

    private ApiHttpClient client;

    @BeforeEach
    void setUp() {
        client = new ApiHttpClient(mockHttpClient, mockObjectMapper);
    }

    @Test
    void getResource_handleOkRequest() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        when(mockResponse.statusCode()).thenReturn(STATUS_OK);
        when(mockResponse.body()).thenReturn("{}");

        when(mockObjectMapper.readValue(anyString(), eq(String.class))).thenReturn("result");

        String result = client.getResource("path/to/resource", "token", String.class);

        assertEquals("result", result);
    }

    @Test
    void getResource_handleBadRequest() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        when(mockResponse.statusCode()).thenReturn(STATUS_BAD_REQUEST);
        when(mockResponse.body()).thenReturn("{}");

        var error = new ApiBadRequest.Error("status", "code", "title");
        when(mockObjectMapper.readValue(anyString(), eq(ApiBadRequest.class)))
                .thenReturn(new ApiBadRequest(new ApiBadRequest.Error[] { error }));

        ApiBadRequestException ex = assertThrows(
                ApiBadRequestException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );

        assertEquals("Request failed with error(s)", ex.getMessage());
        assertNotNull(ex.badRequest);
        assertEquals(1, ex.badRequest.errors().length);
        assertEquals(error, ex.badRequest.errors()[0]);
    }

    @Test
    void getResource_handleServerError() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        var statusCode = 500;
        when(mockResponse.statusCode()).thenReturn(statusCode);

        ApiException ex = assertThrows(
                ApiException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );

        assertEquals("Request failed with status: " + statusCode, ex.getMessage());
    }

    @Test
    void getResource_handleJsonProcessingException() throws Exception {
        when(mockHttpClient.send(any(), eq(HttpResponse.BodyHandlers.ofString()))).thenReturn(mockResponse);

        when(mockResponse.statusCode()).thenReturn(STATUS_OK);
        when(mockResponse.body()).thenReturn("{}");

        when(mockObjectMapper.readValue(anyString(), eq(Object.class))).thenThrow(new JsonMappingException(null, ""));

        ApiException ex = assertThrows(
                ApiException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );

        assertEquals("Error parsing response", ex.getMessage());
        assertInstanceOf(IOException.class, ex.getCause());
    }

    @Test
    void getResource_handleIOException() throws Exception {
        when(mockHttpClient.send(any(), any())).thenThrow(new IOException());

        ApiException ex = assertThrows(
                ApiException.class,
                () -> client.getResource("path/to/resource", "token", Object.class)
        );

        assertEquals("Error during API call", ex.getMessage());
        assertInstanceOf(IOException.class, ex.getCause());
    }
}