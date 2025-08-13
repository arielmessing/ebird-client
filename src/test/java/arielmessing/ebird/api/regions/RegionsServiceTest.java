package arielmessing.ebird.api.regions;

import arielmessing.ebird.api.EBirdApiClient;
import arielmessing.ebird.api.EBirdApiException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionsServiceTest {

    @Mock
    private HttpClient mockClient;

    private EBirdApiClient client;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() throws IOException, InterruptedException {
        client = new EBirdApiClient(mockClient, objectMapper);
    }

    @Test
    void testGetRegionInfoNoQueryParams() throws IOException, InterruptedException, EBirdApiException {
        RegionInfo expected = new RegionInfo();
        expected.setResult("Metro Vancouver District, British Columbia, CA");

        HttpResponse<String> mockResponse = mock(HttpResponse.class);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn(objectMapper.writeValueAsString(expected));

        when(mockClient.send(
                any(HttpRequest.class),
                any(HttpResponse.BodyHandler.class)
        )).thenReturn(mockResponse);

        RegionsService service = new RegionsService(client);
        RegionInfo response = service.getRegionInfo(
                "CA-BC-GV",
                null,
                "api-token");

        assertEquals(expected.getResult(), response.getResult());
    }
}