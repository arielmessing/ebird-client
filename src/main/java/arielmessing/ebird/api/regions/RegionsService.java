package arielmessing.ebird.api.regions;

import arielmessing.ebird.api.ApiClient;

import java.util.Arrays;
import java.util.List;

public class RegionsService {

    private static final char QUERY_SEPARATOR_START = '?';
    private static final char QUERY_SEPARATOR_JOIN = '&';

    private final ApiClient client;

    public RegionsService(ApiClient client) {
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

        if (delim != null) sb.append(querySeparator).append("delim=").append(delim);

        return client.getResource(sb.toString(), token, RegionInfo.class);
    }

    public List<Region> getSubRegionList(
            RegionType regionType,
            String parentRegionCode,
            String token) {

        return Arrays.asList(client.getResource(
                "ref/region/list/%s/%s?fmt=json".formatted(regionType, parentRegionCode),
                token,
                Region[].class));
    }
}
