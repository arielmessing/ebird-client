package arielmessing.ebird.api.hotspots;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import arielmessing.ebird.api.EBirdApiClient;
import arielmessing.ebird.api.EBirdApiException;

import java.util.List;

public class HotspotsService {

    private final EBirdApiClient client;
    private final ObjectMapper objectMapper;

    public HotspotsService(EBirdApiClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    public List<Hotspot> getHotspotsInRegion(String regionCode, Integer back) throws EBirdApiException {
        String responseJson =
                client.getResource(
                        "ref/hotspot/" +
                                regionCode + "?fmt=json");

        try {
            return objectMapper.readValue(responseJson, new TypeReference<>() {});

        } catch (Exception e) {
            throw new EBirdApiException("Error parsing response", e);
        }
    }
}
