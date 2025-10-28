package arielmessing.ebird;

import arielmessing.ebird.client.EbirdApiHttpClient;
import arielmessing.ebird.api.hotspots.Hotspot;
import arielmessing.ebird.api.hotspots.HotspotInfo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class HotspotsServiceIT {

    private static String token;

    private static HotspotsService service;

    @BeforeAll
    static void beforeAll() {
        token = System.getenv("API_KEY");

        service = new HotspotsService(new EbirdApiHttpClient());
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