package arielmessing.ebird.api.geography;

import arielmessing.ebird.api.ApiClient;
import arielmessing.ebird.api.regions.Region;

import java.util.Arrays;
import java.util.List;

public class GeographyService {

    private final ApiClient client;

    public GeographyService(ApiClient client) {
        this.client = client;
    }

    public List<Region> getAdjacentRegions(String regionCode, String token) {
        return Arrays.asList(client.getResource(
                "ref/adjacent/" + regionCode,
                token,
                Region[].class));
    }
}
