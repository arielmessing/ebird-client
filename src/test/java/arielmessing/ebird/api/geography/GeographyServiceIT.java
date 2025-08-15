package arielmessing.ebird.api.geography;

import arielmessing.ebird.api.ApiException;
import arielmessing.ebird.api.ApiHttpClient;
import arielmessing.ebird.api.regions.Region;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeographyServiceIT {

    private static String token;

    private static GeographyService service;

    @BeforeAll
    static void globalSetUp() {
        token = System.getenv("API_KEY");

        ApiHttpClient client =  new ApiHttpClient(HttpClient.newBuilder().build(), new ObjectMapper());
        service = new GeographyService(client);
    }

    @Test
    void getAdjacentRegions() throws ApiException {
        var regionCode = "IL";
        List<Region> regions = service.getAdjacentRegions(regionCode, token);

        assertEquals(1, regions.stream().map(Region::code).filter(regionCode::equals).count());
    }
}