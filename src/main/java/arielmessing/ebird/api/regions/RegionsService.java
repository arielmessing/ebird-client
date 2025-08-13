package arielmessing.ebird.api.regions;

import arielmessing.ebird.api.EBirdApiException;
import arielmessing.ebird.api.EBirdApiClient;

import java.util.Arrays;
import java.util.List;

public class RegionsService {

    private final EBirdApiClient client;

    public RegionsService(EBirdApiClient client) {
        this.client = client;
    }

    public RegionInfo getRegionInfo(
            String regionCode,
            RegionInfoQueryParams queryParams,
            String token) throws EBirdApiException {

        var resourcePath = "ref/region/info/" + regionCode;

        if (queryParams != null) {
            resourcePath += "?";

            if (queryParams.regionNameFormat() != null)
                resourcePath += "regionNameFormat=" + queryParams.regionNameFormat().name() + "&";

            if (queryParams.delim() != null)
                resourcePath += "delim=" + queryParams.delim();
        }

        return client.getResource(resourcePath, token, RegionInfo.class);
    }

    public List<Region> getSubRegionList(
            RegionType regionType,
            String parentRegionCode,
            String token) throws EBirdApiException {

        return Arrays.asList(client.getResource(
                        "ref/region/list/" +
                                regionType.name() +
                                "/" +
                                parentRegionCode +
                                "?fmt=json",
                token,
                Region[].class));
    }
}
