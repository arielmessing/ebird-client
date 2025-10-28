package arielmessing.ebird;

import arielmessing.ebird.api.regions.Region;
import arielmessing.ebird.client.EbirdApiClient;

import java.util.List;

public final class GeographyService {

    private final EbirdApiClient client;

    public GeographyService(EbirdApiClient client) {
        this.client = client;
    }

    public List<Region> getAdjacentRegions(String regionCode, String token) {
        return client.getResourceAsListOf(
                "ref/adjacent/" + regionCode,
                token,
                Region.class);
    }
}
