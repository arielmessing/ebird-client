package arielmessing.ebird.api.taxonomy;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import arielmessing.ebird.api.EBirdApiException;
import arielmessing.ebird.api.EBirdApiClient;

import java.util.List;

public class TaxonomyService {

    private final EBirdApiClient client;
    private final ObjectMapper objectMapper;

    public TaxonomyService(EBirdApiClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    public List<TaxonomyEntry> getEBirdTaxonomy(List<String> species) throws EBirdApiException {
        String responseJson =
                client.getResource(
                        "ref/taxonomy/ebird?fmt=json" +
                                "&species=" + String.join(",", species));

        try {
            return objectMapper.readValue(responseJson, new TypeReference<>() {});

        } catch (Exception e) {
            throw new EBirdApiException("Error parsing response", e);
        }
    }
}
