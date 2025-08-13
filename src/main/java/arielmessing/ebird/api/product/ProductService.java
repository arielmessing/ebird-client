package arielmessing.ebird.api.product;

import arielmessing.ebird.api.ApiException;
import arielmessing.ebird.api.ApiClient;

import java.util.Arrays;
import java.util.List;

public class ProductService {

    private final ApiClient client;

    public ProductService(ApiClient client) {
        this.client = client;
    }

    public List<String> getSpeciesListForRegion(String regionCode, String token) throws ApiException {
        return Arrays.asList(client.getResource(
                "product/spplist/" + regionCode,
                token,
                String[].class));
    }
}
