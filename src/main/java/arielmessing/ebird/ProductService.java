package arielmessing.ebird;

import arielmessing.ebird.api.product.*;
import arielmessing.ebird.client.EbirdApiClient;

import java.time.LocalDate;
import java.util.List;

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

        validateMaxResults(maxResults, 100);

        return client.getResourceAsListOf(
                ProductEndpoints.top100(regionCode, date, rankedBy, maxResults),
                token,
                Contributor.class);
    }

    public List<ChecklistInFeed> getRecentChecklistsFeed(
            String regionCode,
            Integer maxResults,
            String token) {

        validateMaxResults(maxResults, 200);

        return client.getResourceAsListOf(
                ProductEndpoints.recentChecklistsFeed(regionCode, maxResults),
                token,
                ChecklistInFeed.class);
    }

    public List<ChecklistInFeed> getChecklistsFeedOnDate(
            String regionCode,
            LocalDate date,
            SortKey sortKey,
            Integer maxResults,
            String token) {

        validateMaxResults(maxResults, 200);

        return client.getResourceAsListOf(
                ProductEndpoints.checklistsFeedOnDate(regionCode, date, sortKey, maxResults),
                token,
                ChecklistInFeed.class);
    }

    public Statistics getRegionalStatisticsOnDate(String regionCode, LocalDate date, String token) {
        return client.getResource(
                ProductEndpoints.regionalStatisticsOnDate(regionCode, date),
                token,
                Statistics.class);
    }

    public List<String> getSpeciesListForRegion(String regionCode, String token) {
        return client.getResourceAsListOf(
                ProductEndpoints.speciesListForRegion(regionCode),
                token,
                String.class);
    }

    public Checklist getChecklist(String subId, String token) {
        return client.getResource(
                ProductEndpoints.viewChecklist(subId),
                token,
                Checklist.class);
    }

    private static void validateMaxResults(Integer maxResults, int max) {
        if (maxResults != null && (maxResults < 1 || maxResults > max))
            throw new IllegalArgumentException("maxResults is outside of range 1-%d".formatted(max));
    }
}
