package arielmessing.ebird.api.hotspots;

import arielmessing.ebird.api.ApiException;
import arielmessing.ebird.api.ApiHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HotspotsServiceIT {

    private static String token;

    private static HotspotsService service;

    @BeforeAll
    static void globalSetUp() {
        token = System.getenv("API_KEY");

        ApiHttpClient client =  new ApiHttpClient(HttpClient.newBuilder().build(), new ObjectMapper());
        service = new HotspotsService(client);
    }

    @Test
    void getHotspotsInRegion() throws ApiException {
        List<Hotspot> hotspotsInRegion = service.getHotspotsInRegion("GB-ENG-KEN", null, token);

        assertFalse(hotspotsInRegion.isEmpty());
    }

    @Test
    void getHotspotInfo() throws ApiException {
        String locationCode = "L918439";
        HotspotInfo info = service.getHotspotInfo(locationCode, token);

        assertEquals(locationCode, info.locId());
    }

    @Test
    void getNearbyHotspots() throws ApiException {
        List<Hotspot> nearbyHotspots = service.getNearbyHotspots(50.9309436, 0.9394598, null, null, token);

        assertFalse(nearbyHotspots.isEmpty());
    }
}