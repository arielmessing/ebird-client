package arielmessing.ebird.api.geography;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import arielmessing.ebird.api.EBirdApiClient;
import arielmessing.ebird.api.EBirdApiException;
import arielmessing.ebird.api.regions.Region;

import java.util.List;

public class GeographyService {

    private final EBirdApiClient client;
    private final ObjectMapper objectMapper;

    public GeographyService(EBirdApiClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    public List<Region> getAdjacentRegions(String regionCode) throws EBirdApiException {
        String responseJson = client.getResource("ref/adjacent/" + regionCode);

        try {
            return objectMapper.readValue(responseJson, new TypeReference<>() {});

        } catch (Exception e) {
            throw new EBirdApiException("Error parsing response", e);
        }
    }
}
