package arielmessing.ebird;

import arielmessing.ebird.api.product.*;
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

    public List<ChecklistInFeed> getRecentChecklistsFeed(
            String regionCode,
            Integer maxResults,
            String token) {

        validateMaxResults(maxResults);

        var path = new StringBuilder("product/lists/").append(regionCode);
        if (maxResults != null) {
            path.append("?maxResults=").append(maxResults);
        }

        return client.getResourceAsListOf(path.toString(), token, ChecklistInFeed.class);
    }

    public List<ChecklistInFeed> getChecklistsFeedOnDate(
            String regionCode,
            LocalDate date,
            SortKey sortKey,
            Integer maxResults,
            String token) {

        validateMaxResults(maxResults);

        var requestParams = new StringJoiner("&");
        if (sortKey != null) requestParams.add("sortKey=" + sortKey);
        if (maxResults != null) requestParams.add("maxResults=" + maxResults);

        return client.getResourceAsListOf(
                "product/lists/%s/%d/%d/%d?%s".formatted(
                        regionCode, date.getYear(), date.getMonthValue(), date.getDayOfMonth(), requestParams),
                token,
                ChecklistInFeed.class);
    }

    public Statistics getRegionalStatisticsOnDate(String regionCode, LocalDate date, String token) {
        return client.getResource(
                "product/stats/%s/%d/%d/%d".formatted(
                        regionCode, date.getYear(), date.getMonthValue(), date.getDayOfMonth()),
                token,
                Statistics.class);
    }

    public List<String> getSpeciesListForRegion(String regionCode, String token) {
        return client.getResourceAsListOf(
                "product/spplist/" + regionCode,
                token,
                String.class);
    }

    public Checklist getChecklist(String subId, String token) {
        return client.getResource(
                "product/checklist/view/" + subId,
                token,
                Checklist.class);
    }

    private static void validateMaxResults(Integer maxResults) {
        if (maxResults != null && (maxResults < 1 || maxResults > 200))
            throw new IllegalArgumentException("maxResults is out of range 1-200");
    }
}
