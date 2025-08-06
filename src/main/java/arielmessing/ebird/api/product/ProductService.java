package arielmessing.ebird.api.product;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import arielmessing.ebird.api.EBirdApiException;
import arielmessing.ebird.api.EBirdApiClient;

import java.util.List;

public class ProductService {

    private final EBirdApiClient client;
    private final ObjectMapper objectMapper;

    public ProductService(EBirdApiClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    public List<String> getSpeciesListForRegion(String regionCode) throws EBirdApiException {
        String responseJson =
                client.getResource(
                        "product/spplist/" +
                                regionCode);

        try {
            return objectMapper.readValue(responseJson, new TypeReference<>() {});

        } catch (Exception e) {
            throw new EBirdApiException("Error parsing response", e);
        }
    }
}
