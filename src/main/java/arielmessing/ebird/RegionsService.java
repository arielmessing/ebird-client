package arielmessing.ebird;

import arielmessing.ebird.api.regions.Region;
import arielmessing.ebird.api.regions.RegionInfo;
import arielmessing.ebird.api.regions.RegionNameFormat;
import arielmessing.ebird.api.regions.RegionType;
import arielmessing.ebird.client.EbirdApiClient;

import java.util.List;
import java.util.StringJoiner;

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

        var requestParams = new StringJoiner("&");
        if (regionNameFormat != null)           requestParams.add("regionNameFormat=" + regionNameFormat);
        if (delim != null && ! delim.isEmpty()) requestParams.add("delim=" + delim);

        return client.getResource(
                "ref/region/info/%s?%s".formatted(regionCode, requestParams),
                token,
                RegionInfo.class);
    }

    public List<Region> getSubRegionList(
            RegionType subRegionType,
            String parentRegionCode,
            String token) {

        return client.getResourceAsListOf(
                "ref/region/list/%s/%s?fmt=json".formatted(subRegionType, parentRegionCode),
                token,
                Region.class);
    }
}
