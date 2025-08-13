package arielmessing.ebird.api.hotspots;

import arielmessing.ebird.api.ApiClient;
import arielmessing.ebird.api.ApiException;

import java.util.Arrays;
import java.util.List;

public class HotspotsService {

    private final ApiClient client;

    public HotspotsService(ApiClient client) {
        this.client = client;
    }

    public List<Hotspot> getHotspotsInRegion(String regionCode, Integer back, String token) throws ApiException {
        return Arrays.asList(client.getResource(
                "ref/hotspot/" + regionCode + "?fmt=json",
                token,
                Hotspot[].class));
    }
}
