package arielmessing.ebird.api.hotspots;

import arielmessing.ebird.api.ApiClient;
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

        ApiClient client = new ApiClient(HttpClient.newBuilder().build(), new ObjectMapper());
        service = new HotspotsService(client);
    }

    @Test
    void getHotspotsInRegion() {
        List<Hotspot> hotspotsInRegion = service.getHotspotsInRegion("GB-ENG-KEN", null, token);

        assertFalse(hotspotsInRegion.isEmpty());
    }

    @Test
    void getHotspotInfo() {
        String locationCode = "L918439";
        HotspotInfo info = service.getHotspotInfo(locationCode, token);

        assertEquals(locationCode, info.locId());
    }

    @Test
    void getNearbyHotspots() {
        List<Hotspot> nearbyHotspots = service.getNearbyHotspots(50.9309436, 0.9394598, null, null, token);

        assertFalse(nearbyHotspots.isEmpty());
    }
}