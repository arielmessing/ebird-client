package arielmessing.ebird;

import arielmessing.ebird.client.EbirdApiHttpClient;
import arielmessing.ebird.api.regions.Region;
import arielmessing.ebird.api.regions.RegionInfo;
import arielmessing.ebird.api.regions.RegionNameFormat;
import arielmessing.ebird.api.regions.RegionType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegionsServiceIT {

    private static String token;

    private static RegionsService service;

    @BeforeAll
    static void beforeAll() {
        token = System.getenv("API_KEY");

        service = new RegionsService(new EbirdApiHttpClient());
    }

    @Test
    void getRegionInfo() {
        String delim = "@@";

        RegionInfo info = service.getRegionInfo("IL-JM", RegionNameFormat.full, delim, token);

        assertTrue(info.result().contains(delim));
    }

    @Test
    void getSubRegionList() {
        List<Region> regions = service.getSubRegionList(RegionType.subnational1, "IL", token);

        assertFalse(regions.isEmpty());
    }
}