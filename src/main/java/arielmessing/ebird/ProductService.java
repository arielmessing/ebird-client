package arielmessing.ebird;

import arielmessing.ebird.api.product.Contributor;
import arielmessing.ebird.api.product.RankedBy;
import arielmessing.ebird.client.EbirdApiClient;

import java.time.LocalDate;
import java.util.List;

public final class ProductService {

    private static final char QUERY_SEPARATOR_START = '?';
    private static final char QUERY_SEPARATOR_JOIN = '&';

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

        StringBuilder sb = new StringBuilder("product/top100/").append(regionCode)
                .append("/").append(date.getYear())
                .append("/").append(date.getMonthValue())
                .append("/").append(date.getDayOfMonth());

        char querySeparator = QUERY_SEPARATOR_START;

        if (rankedBy != null) {
            sb.append(querySeparator).append("rankedBy=").append(rankedBy.value);

            querySeparator = QUERY_SEPARATOR_JOIN;
        }

        if (maxResults != null) sb.append(querySeparator).append("maxResults=").append(maxResults);

        return client.getResourceAsListOf(sb.toString(), token, Contributor.class);
    }

    public List<String> getSpeciesListForRegion(String regionCode, String token) {
        return client.getResourceAsListOf(
                "product/spplist/" + regionCode,
                token,
                String.class);
    }
}
