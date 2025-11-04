package arielmessing.ebird.api.product;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static arielmessing.ebird.EndpointsHelper.build;

public class ProductEndpoints {

    static final String TOP_100 = "product/top100/{{regionCode}}/{{y}}/{{m}}/{{d}}";

    static final String RECENT_CHECKLISTS_FEED = "product/lists/{{regionCode}}";

    static final String CHECKLIST_FEED_ON_DATE = "product/lists/{{regionCode}}/{{y}}/{{m}}/{{d}}";

    static final String REGIONAL_STATISTICS_ON_DATE = "product/stats/{{regionCode}}/{{y}}/{{m}}/{{d}}";

    static final String SPECIES_LIST_FOR_REGION = "product/spplist/{{regionCode}}";

    static final String VIEW_CHECKLIST = "product/checklist/view/{{subId}}";

    public static String top100 (
            String regionCode,
            LocalDate date,
            RankedBy rankedBy,
            Integer maxResults) {

        var optionalParams = new HashMap<String, Object>();
        if (rankedBy != null) optionalParams.put("rankedBy", rankedBy.value);
        if (maxResults != null) optionalParams.put("maxResults", maxResults);

        return build(
                TOP_100,
                Map.of(
                        "regionCode", regionCode,
                        "y", date.getYear(),
                        "m", date.getMonthValue(),
                        "d", date.getDayOfMonth()
                ),
                optionalParams);
    }

    public static String recentChecklistsFeed(String regionCode, Integer maxResults) {

        var optionalParams = new HashMap<String, Object>();
        if (maxResults != null) optionalParams.put("maxResults", maxResults);

        return build(RECENT_CHECKLISTS_FEED, Map.of("regionCode", regionCode), optionalParams);
    }

    public static String checklistsFeedOnDate(
            String regionCode,
            LocalDate date,
            SortKey sortKey,
            Integer maxResults) {

        var optionalParams = new HashMap<String, Object>();
        if (sortKey != null) optionalParams.put("sortKey", sortKey);
        if (maxResults != null) optionalParams.put("maxResults", maxResults);

        return build(
                CHECKLIST_FEED_ON_DATE,
                Map.of(
                        "regionCode", regionCode,
                        "y", date.getYear(),
                        "m", date.getMonthValue(),
                        "d", date.getDayOfMonth()
                ),
                optionalParams);
    }

    public static String regionalStatisticsOnDate(String regionCode, LocalDate date) {
        return build(
                REGIONAL_STATISTICS_ON_DATE,
                Map.of(
                        "regionCode", regionCode,
                        "y", date.getYear(),
                        "m", date.getMonthValue(),
                        "d", date.getDayOfMonth()
                ),
                Map.of());
    }

    public static String speciesListForRegion(String regionCode) {
        return build(SPECIES_LIST_FOR_REGION, Map.of("regionCode", regionCode), Map.of());
    }

    public static String viewChecklist(String subId) {
        return build(VIEW_CHECKLIST, Map.of("subId", subId), Map.of());
    }
}
