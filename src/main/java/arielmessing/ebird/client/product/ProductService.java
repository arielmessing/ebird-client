package arielmessing.ebird.client.product;

import arielmessing.ebird.client.EbirdClient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ProductService {

    private static final char QUERY_SEPARATOR_START = '?';
    private static final char QUERY_SEPARATOR_JOIN = '&';

    private final EbirdClient client;

    public ProductService(EbirdClient client) {
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

        return Arrays.asList(client.getResource(sb.toString(), token, Contributor[].class));
    }

    public List<String> getSpeciesListForRegion(String regionCode, String token) {
        return Arrays.asList(client.getResource(
                "product/spplist/" + regionCode,
                token,
                String[].class));
    }
}
