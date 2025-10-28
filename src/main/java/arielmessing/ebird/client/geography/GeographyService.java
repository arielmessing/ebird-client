package arielmessing.ebird.client.geography;

import arielmessing.ebird.client.EbirdClient;
import arielmessing.ebird.client.regions.Region;

import java.util.Arrays;
import java.util.List;

public class GeographyService {

    private final EbirdClient client;

    public GeographyService(EbirdClient client) {
        this.client = client;
    }

    public List<Region> getAdjacentRegions(String regionCode, String token) {
        return Arrays.asList(client.getResource(
                "ref/adjacent/" + regionCode,
                token,
                Region[].class));
    }
}
