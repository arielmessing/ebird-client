package arielmessing.ebird.client.regions;

import arielmessing.ebird.client.EbirdClient;
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

        EbirdClient client = new EbirdClient(HttpClient.newBuilder().build(), new ObjectMapper());
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