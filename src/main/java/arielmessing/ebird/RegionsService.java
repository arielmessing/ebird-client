package arielmessing.ebird;

import arielmessing.ebird.api.regions.*;
import arielmessing.ebird.client.EbirdApiClient;

import java.util.List;

public final class RegionsService {

    private final EbirdApiClient client;

    public RegionsService(EbirdApiClient client) {
        this.client = client;
    }

    public RegionInfo getRegionInfo(
            String regionCode,
            RegionNameFormat regionNameFormat,
            String delim,
            String token) {

        return client.getResource(
                RegionsEndpoints.regionInfo(regionCode, regionNameFormat, delim),
                token,
                RegionInfo.class);
    }

    public List<Region> getSubRegionList(
            RegionType subRegionType,
            String parentRegionCode,
            String token) {

        return client.getResourceAsListOf(
                RegionsEndpoints.subRegionList(subRegionType, parentRegionCode),
                token,
                Region.class);
    }
}
