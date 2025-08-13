package arielmessing.ebird.api.product;

import arielmessing.ebird.api.EBirdApiException;
import arielmessing.ebird.api.EBirdApiClient;

import java.util.Arrays;
import java.util.List;

public class ProductService {

    private final EBirdApiClient client;

    public ProductService(EBirdApiClient client) {
        this.client = client;
    }

    public List<String> getSpeciesListForRegion(String regionCode, String token) throws EBirdApiException {
        return Arrays.asList(client.getResource(
                "product/spplist/" + regionCode,
                token,
                String[].class));
    }
}
