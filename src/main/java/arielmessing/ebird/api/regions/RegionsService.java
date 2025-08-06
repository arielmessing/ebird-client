package arielmessing.ebird.api.regions;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import arielmessing.ebird.api.EBirdApiException;
import arielmessing.ebird.api.EBirdApiClient;

import java.util.List;

public class RegionsService {

    private final EBirdApiClient client;
    private final ObjectMapper objectMapper;

    public RegionsService(EBirdApiClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    public RegionInfo getRegionInfo(String regionCode, RegionInfoQueryParams queryParams) throws EBirdApiException {
        var resourcePath = "ref/region/info/" + regionCode;

        if (queryParams != null) {
            resourcePath += "?";

            if (queryParams.regionNameFormat() != null)
                resourcePath += "regionNameFormat=" + queryParams.regionNameFormat().name() + "&";

            if (queryParams.delim() != null)
                resourcePath += "delim=" + queryParams.delim();
        }

        String responseJson = client.getResource(resourcePath);

        try {
            return objectMapper.readValue(responseJson, RegionInfo.class);

        } catch (Exception e) {
            throw new EBirdApiException("Error parsing response", e);
        }
    }

    public List<Region> getSubRegionList(
            RegionType regionType,
            String parentRegionCode)  throws EBirdApiException {

        String responseJson =
                client.getResource(
                        "ref/region/list/" +
                                regionType.name() +
                                "/" +
                                parentRegionCode +
                                "?fmt=json");

        try {
            return objectMapper.readValue(responseJson, new TypeReference<>() {});

        } catch (Exception e) {
            throw new EBirdApiException("Error parsing response", e);
        }
    }
}
