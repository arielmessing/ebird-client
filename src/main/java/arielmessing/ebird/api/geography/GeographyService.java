package arielmessing.ebird.api.geography;

import arielmessing.ebird.api.EBirdApiClient;
import arielmessing.ebird.api.EBirdApiException;
import arielmessing.ebird.api.regions.Region;

import java.util.Arrays;
import java.util.List;

public class GeographyService {

    private final EBirdApiClient client;

    public GeographyService(EBirdApiClient client) {
        this.client = client;
    }

    public List<Region> getAdjacentRegions(String regionCode, String token) throws EBirdApiException {
        return Arrays.asList(client.getResource(
                "ref/adjacent/" + regionCode,
                token,
                Region[].class));
    }
}
