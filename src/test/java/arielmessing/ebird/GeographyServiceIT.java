package arielmessing.ebird;

import arielmessing.ebird.client.EbirdApiHttpClient;
import arielmessing.ebird.api.regions.Region;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GeographyServiceIT {

    private static String token;

    private static GeographyService service;

    @BeforeAll
    static void beforeAll() {
        token = System.getenv("API_KEY");

        service = new GeographyService(new EbirdApiHttpClient());
    }

    @Test
    void getAdjacentRegions() {
        var regionCode = "IL";
        List<Region> regions = service.getAdjacentRegions(regionCode, token);

        assertEquals(1, regions.stream().map(Region::code).filter(regionCode::equals).count());
    }
}