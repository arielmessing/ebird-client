package arielmessing.ebird.api.taxonomy;

import arielmessing.ebird.api.EBirdApiException;
import arielmessing.ebird.api.EBirdApiClient;

import java.util.Arrays;
import java.util.List;

public class TaxonomyService {

    private final EBirdApiClient client;

    public TaxonomyService(EBirdApiClient client) {
        this.client = client;
    }

    public List<TaxonomyEntry> getEBirdTaxonomy(List<String> species, String token) throws EBirdApiException {
        return Arrays.asList(client.getResource(
                "ref/taxonomy/ebird?fmt=json" +
                        "&species=" + String.join(",", species),
                token,
                TaxonomyEntry[].class));
    }
}
