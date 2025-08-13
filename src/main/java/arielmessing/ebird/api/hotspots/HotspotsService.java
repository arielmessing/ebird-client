package arielmessing.ebird.api.hotspots;

import arielmessing.ebird.api.EBirdApiClient;
import arielmessing.ebird.api.EBirdApiException;

import java.util.Arrays;
import java.util.List;

public class HotspotsService {

    private final EBirdApiClient client;

    public HotspotsService(EBirdApiClient client) {
        this.client = client;
    }

    public List<Hotspot> getHotspotsInRegion(String regionCode, Integer back, String token) throws EBirdApiException {
        return Arrays.asList(client.getResource(
                "ref/hotspot/" + regionCode + "?fmt=json",
                token,
                Hotspot[].class));
    }
}
