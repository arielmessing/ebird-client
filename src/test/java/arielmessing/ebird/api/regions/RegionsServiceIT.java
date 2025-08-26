package arielmessing.ebird.api.regions;

import arielmessing.ebird.api.ApiHttpClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RegionsServiceIT {

    private static String token;

    private static RegionsService service;

    @BeforeAll
    static void globalSetUp() {
        token = System.getenv("API_KEY");

        ApiHttpClient client = new ApiHttpClient(HttpClient.newBuilder().build(), new ObjectMapper());
        service = new RegionsService(client);
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