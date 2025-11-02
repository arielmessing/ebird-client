package arielmessing.ebird;

import arielmessing.ebird.api.product.Contributor;
import arielmessing.ebird.api.product.RankedBy;
import arielmessing.ebird.client.EbirdApiClient;

import java.time.LocalDate;
import java.util.List;
import java.util.StringJoiner;

public final class ProductService {

    private final EbirdApiClient client;

    public ProductService(EbirdApiClient client) {
        this.client = client;
    }

    public List<Contributor> getTop100Contributors (
            String regionCode,
            LocalDate date,
            RankedBy rankedBy,
            Integer maxResults,
            String token) {

        var requestParams = new StringJoiner("&");
        if (rankedBy != null)   requestParams.add("rankedBy=" + rankedBy.value);
        if (maxResults != null) requestParams.add("maxResults=" + maxResults);

        return client.getResourceAsListOf(
                "product/top100/%s/%d/%d/%d?%s".formatted(
                        regionCode, date.getYear(), date.getMonthValue(), date.getDayOfMonth(), requestParams),
                token,
                Contributor.class);
    }

    public List<String> getSpeciesListForRegion(String regionCode, String token) {
        return client.getResourceAsListOf(
                "product/spplist/" + regionCode,
                token,
                String.class);
    }
}
