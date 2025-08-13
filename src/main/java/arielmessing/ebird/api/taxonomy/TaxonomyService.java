package arielmessing.ebird.api.taxonomy;

import arielmessing.ebird.api.ApiClient;
import arielmessing.ebird.api.ApiException;

import java.util.Arrays;
import java.util.List;

public class TaxonomyService {

    private final ApiClient client;

    public TaxonomyService(ApiClient client) {
        this.client = client;
    }

    public List<TaxonomyEntry> getEBirdTaxonomy(List<String> species, String token) throws ApiException {
        return Arrays.asList(client.getResource(
                "ref/taxonomy/ebird?fmt=json" +
                        "&species=" + String.join(",", species),
                token,
                TaxonomyEntry[].class));
    }
}
