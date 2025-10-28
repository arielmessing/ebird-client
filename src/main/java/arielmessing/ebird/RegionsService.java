package arielmessing.ebird;

import arielmessing.ebird.api.regions.Region;
import arielmessing.ebird.api.regions.RegionInfo;
import arielmessing.ebird.api.regions.RegionNameFormat;
import arielmessing.ebird.api.regions.RegionType;
import arielmessing.ebird.client.EbirdApiClient;

import java.util.List;

public final class RegionsService {

    private static final char QUERY_SEPARATOR_START = '?';
    private static final char QUERY_SEPARATOR_JOIN = '&';

    private final EbirdApiClient client;

    public RegionsService(EbirdApiClient client) {
        this.client = client;
    }

    public RegionInfo getRegionInfo(
            String regionCode,
            RegionNameFormat regionNameFormat,
            String delim,
            String token) {

        StringBuilder sb = new StringBuilder("ref/region/info/").append(regionCode);

        char querySeparator = QUERY_SEPARATOR_START;

        if (regionNameFormat != null) {
            sb.append(querySeparator).append("regionNameFormat=").append(regionNameFormat);

            querySeparator = QUERY_SEPARATOR_JOIN;
        }

        if (delim != null && ! delim.isEmpty()) sb.append(querySeparator).append("delim=").append(delim);

        return client.getResource(sb.toString(), token, RegionInfo.class);
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
