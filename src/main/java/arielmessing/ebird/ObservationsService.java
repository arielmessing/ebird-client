package arielmessing.ebird;

import arielmessing.ebird.api.observations.Observation;
import arielmessing.ebird.api.observations.ObservationDetail;
import arielmessing.ebird.api.observations.ObservationRankBy;
import arielmessing.ebird.api.observations.ObservationsSort;
import arielmessing.ebird.api.taxonomy.TaxonomyCategory;
import arielmessing.ebird.client.EbirdApiClient;

import java.time.LocalDate;
import java.util.*;

public final class ObservationsService {

    public record Location(double latitude, double longitude, Integer distance) {}

    static final String HISTORIC =          "data/obs/{{regionCode}}/historic/{{y}}/{{m}}/{{d}}";
    static final String RECENT_IN_REGION =  "data/obs/{{regionCode}}/recent";
    static final String NOTABLE_IN_REGION = "data/obs/{{regionCode}}/recent/notable";
    static final String SPECIES_IN_REGION = "data/obs/{{regionCode}}/recent/{{speciesCode}}";
    static final String RECENT_AROUND =     "data/obs/geo/recent?lat={{lat}}&lng={{lng}}";
    static final String NOTABLE_AROUND =    "data/obs/geo/recent/notable?lat={{lat}}&lng={{lng}}";
    static final String SPECIES_AROUND =    "data/obs/geo/recent/{{speciesCode}}?lat={{lat}}&lng={{lng}}";
    static final String NEAREST_SPECIES =   "data/nearest/geo/recent/{{speciesCode}}?lat={{lat}}&lng={{lng}}";

    static final String REGION_CODE = "regionCode";

    static final String SPECIES_CODE = "speciesCode";

    static final String YEAR = "y";

    static final String MONTH = "m";

    static final String DAY = "d";

    static final String LOCATION_CODES = "r";

    static final String LATITUDE = "lat";
    static final double LATITUDE_MIN = -90.0;
    static final double LATITUDE_MAX = 90.0;

    static final String LONGITUDE = "lng";
    static final double LONGITUDE_MIN = -180.0;
    static final double LONGITUDE_MAX = 180.0;

    static final String DISTANCE = "dist";
    static final int DISTANCE_MIN = 1;
    static final int DISTANCE_MAX = 50;

    static final String BACK = "back";
    static final int BACK_MIN = 1;
    static final int BACK_MAX = 30;

    static final String CATEGORY = "cat";

    static final String HOTSPOT = "hotspot";

    static final String INCLUDE_PROVISIONAL = "includeProvisional";

    static final String RANK_BY = "rank";

    static final String DETAIL = "detail";

    static final String SORT = "sort";

    static final String MAX_RESULTS = "maxResults";
    static final int MAX_RESULTS_MIN = 1;
    static final int MAX_RESULTS_MAX = 10_000;
    static final int MAX_RESULTS_NEAREST_OBS_MAX = 3_000;

    static final String SPP_LOCALE = "sppLocale";

    private final EbirdApiClient client;

    public ObservationsService(EbirdApiClient client) {
        this.client = client;
    }

    public List<Observation> getHistoricObservationsOnDate(
            String regionCode,
            LocalDate date,
            List<String> locationCodes,
            TaxonomyCategory category,
            Boolean hotspot,
            Boolean includeProvisional,
            ObservationRankBy rankBy,
            ObservationDetail detail,
            Integer maxResults,
            String sppLocale,
            String token) {

        verifyNotNull(REGION_CODE, regionCode);
        verifyNotNull("date", date);
        if (date.getYear() < 1800) throw new IllegalArgumentException("Year is before 1800");
        verifyIntegerInRange(MAX_RESULTS, maxResults, MAX_RESULTS_MIN, MAX_RESULTS_MAX);

        var optionalParams = new HashMap<String, Object>();
        putLocationCodesIfNotEmpty(locationCodes, optionalParams);
        putOptionalParamIfNotNull(CATEGORY, category, optionalParams);
        putOptionalParamIfNotNull(HOTSPOT, hotspot, optionalParams);
        putOptionalParamIfNotNull(INCLUDE_PROVISIONAL, includeProvisional, optionalParams);
        putOptionalParamIfNotNull(RANK_BY, rankBy, optionalParams);
        putOptionalParamIfNotNull(DETAIL, detail, optionalParams);
        putOptionalParamIfNotNull(MAX_RESULTS, maxResults, optionalParams);
        putOptionalParamIfNotNull(SPP_LOCALE, sppLocale, optionalParams);

        return getObservations(HISTORIC,
                Map.of(
                        REGION_CODE, regionCode,
                        YEAR, date.getYear(),
                        MONTH, date.getMonthValue(),
                        DAY, date.getDayOfMonth()
                ),
                optionalParams,
                token);
    }

    public List<Observation> getRecentObservationsInRegion(
            String regionCode,
            List<String> locationCodes,
            Integer back,
            TaxonomyCategory category,
            Boolean hotspot,
            Boolean includeProvisional,
            Integer maxResults,
            String sppLocale,
            String token) {

        verifyNotNull(REGION_CODE, regionCode);
        verifyIntegerInRange(BACK, back, BACK_MIN, BACK_MAX);
        verifyIntegerInRange(MAX_RESULTS, maxResults, MAX_RESULTS_MIN, MAX_RESULTS_MAX);

        var optionalParams = new HashMap<String, Object>();
        putLocationCodesIfNotEmpty(locationCodes, optionalParams);
        putOptionalParamIfNotNull(BACK, back, optionalParams);
        putOptionalParamIfNotNull(CATEGORY, category, optionalParams);
        putOptionalParamIfNotNull(HOTSPOT, hotspot, optionalParams);
        putOptionalParamIfNotNull(INCLUDE_PROVISIONAL, includeProvisional, optionalParams);
        putOptionalParamIfNotNull(MAX_RESULTS, maxResults, optionalParams);
        putOptionalParamIfNotNull(SPP_LOCALE, sppLocale, optionalParams);

        return getObservations(RECENT_IN_REGION,
                Map.of(REGION_CODE, regionCode),
                optionalParams,
                token);
    }

    public List<Observation> getRecentNotableObservationsInRegion(
            String regionCode,
            List<String> locationCodes,
            Integer back,
            Boolean hotspot,
            ObservationDetail detail,
            Integer maxResults,
            String sppLocale,
            String token) {

        verifyNotNull(REGION_CODE, regionCode);
        verifyIntegerInRange(BACK, back, BACK_MIN, BACK_MAX);
        verifyIntegerInRange(MAX_RESULTS, maxResults, MAX_RESULTS_MIN, MAX_RESULTS_MAX);

        var optionalParams = new HashMap<String, Object>();
        putLocationCodesIfNotEmpty(locationCodes, optionalParams);
        putOptionalParamIfNotNull(BACK, back, optionalParams);
        putOptionalParamIfNotNull(HOTSPOT, hotspot, optionalParams);
        putOptionalParamIfNotNull(DETAIL, detail, optionalParams);
        putOptionalParamIfNotNull(MAX_RESULTS, maxResults, optionalParams);
        putOptionalParamIfNotNull(SPP_LOCALE, sppLocale, optionalParams);

        return getObservations(NOTABLE_IN_REGION,
                Map.of(REGION_CODE, regionCode),
                optionalParams,
                token);
    }

    public List<Observation> getRecentObservationsOfSpeciesInRegion(
            String regionCode,
            String speciesCode,
            List<String> locationCodes,
            Integer back,
            Boolean hotspot,
            Boolean includeProvisional,
            Integer maxResults,
            String sppLocale,
            String token) {

        verifyNotNull(REGION_CODE, regionCode);
        verifyNotNull(SPECIES_CODE, speciesCode);
        verifyIntegerInRange(BACK, back, BACK_MIN, BACK_MAX);
        verifyIntegerInRange(MAX_RESULTS, maxResults, MAX_RESULTS_MIN, MAX_RESULTS_MAX);

        var optionalParams = new HashMap<String, Object>();
        putLocationCodesIfNotEmpty(locationCodes, optionalParams);
        putOptionalParamIfNotNull(BACK, back, optionalParams);
        putOptionalParamIfNotNull(HOTSPOT, hotspot, optionalParams);
        putOptionalParamIfNotNull(INCLUDE_PROVISIONAL, includeProvisional, optionalParams);
        putOptionalParamIfNotNull(MAX_RESULTS, maxResults, optionalParams);
        putOptionalParamIfNotNull(SPP_LOCALE, sppLocale, optionalParams);

        return getObservations(SPECIES_IN_REGION,
                Map.of(
                        REGION_CODE, regionCode,
                        SPECIES_CODE, speciesCode
                ),
                optionalParams,
                token);
    }

    public List<Observation> getRecentNearbyObservations(
            Location location,
            Integer back,
            TaxonomyCategory category,
            Boolean hotspot,
            Boolean includeProvisional,
            ObservationsSort sort,
            Integer maxResults,
            String sppLocale,
            String token) {

        verifyLocation(location);
        verifyIntegerInRange(BACK, back, BACK_MIN, BACK_MAX);
        verifyIntegerInRange(MAX_RESULTS, maxResults, MAX_RESULTS_MIN, MAX_RESULTS_MAX);

        var optionalParams = new HashMap<String, Object>();
        putOptionalParamIfNotNull(DISTANCE, location.distance, optionalParams);
        putOptionalParamIfNotNull(BACK, back, optionalParams);
        putOptionalParamIfNotNull(CATEGORY, category, optionalParams);
        putOptionalParamIfNotNull(HOTSPOT, hotspot, optionalParams);
        putOptionalParamIfNotNull(INCLUDE_PROVISIONAL, includeProvisional, optionalParams);
        putOptionalParamIfNotNull(SORT, sort, optionalParams);
        putOptionalParamIfNotNull(MAX_RESULTS, maxResults, optionalParams);
        putOptionalParamIfNotNull(SPP_LOCALE, sppLocale, optionalParams);

        return getObservations(RECENT_AROUND,
                Map.of(
                        LATITUDE, location.latitude,
                        LONGITUDE, location.longitude
                ),
                optionalParams,
                token);
    }

    public List<Observation> getRecentNearbyNotableObservations(
            Location location,
            Integer back,
            Boolean hotspot,
            ObservationDetail detail,
            Integer maxResults,
            String sppLocale,
            String token) {

        verifyLocation(location);
        verifyIntegerInRange(BACK, back, BACK_MIN, BACK_MAX);
        verifyIntegerInRange(MAX_RESULTS, maxResults, MAX_RESULTS_MIN, MAX_RESULTS_MAX);

        var optionalParams = new HashMap<String, Object>();
        putOptionalParamIfNotNull(DISTANCE, location.distance, optionalParams);
        putOptionalParamIfNotNull(BACK, back, optionalParams);
        putOptionalParamIfNotNull(HOTSPOT, hotspot, optionalParams);
        putOptionalParamIfNotNull(DETAIL, detail, optionalParams);
        putOptionalParamIfNotNull(MAX_RESULTS, maxResults, optionalParams);
        putOptionalParamIfNotNull(SPP_LOCALE, sppLocale, optionalParams);

        return getObservations(NOTABLE_AROUND,
                Map.of(
                        LATITUDE, location.latitude,
                        LONGITUDE, location.longitude
                ),
                optionalParams,
                token);
    }

    public List<Observation> getRecentNearbyObservationsOfSpecies(
            String speciesCode,
            Location location,
            Integer back,
            Boolean hotspot,
            Boolean includeProvisional,
            Integer maxResults,
            String sppLocale,
            String token) {

        verifyNotNull(SPECIES_CODE, speciesCode);
        verifyLocation(location);
        verifyIntegerInRange(BACK, back, BACK_MIN, BACK_MAX);
        verifyIntegerInRange(MAX_RESULTS, maxResults, MAX_RESULTS_MIN, MAX_RESULTS_MAX);

        var optionalParams = new HashMap<String, Object>();
        putOptionalParamIfNotNull(DISTANCE, location.distance, optionalParams);
        putOptionalParamIfNotNull(BACK, back, optionalParams);
        putOptionalParamIfNotNull(HOTSPOT, hotspot, optionalParams);
        putOptionalParamIfNotNull(INCLUDE_PROVISIONAL, includeProvisional, optionalParams);
        putOptionalParamIfNotNull(MAX_RESULTS, maxResults, optionalParams);
        putOptionalParamIfNotNull(SPP_LOCALE, sppLocale, optionalParams);

        return getObservations(SPECIES_AROUND,
                Map.of(
                        SPECIES_CODE, speciesCode,
                        LATITUDE, location.latitude,
                        LONGITUDE, location.longitude
                ),
                optionalParams,
                token);
    }

    public List<Observation> getNearestObservationsOfSpecies(
            String speciesCode,
            Location location,
            Integer back,
            Boolean hotspot,
            Boolean includeProvisional,
            Integer maxResults,
            String sppLocale,
            String token) {

        verifyNotNull(SPECIES_CODE, speciesCode);
        verifyLocation(location);
        verifyIntegerInRange(BACK, back, BACK_MIN, BACK_MAX);
        verifyIntegerInRange(MAX_RESULTS, maxResults, MAX_RESULTS_MIN, MAX_RESULTS_NEAREST_OBS_MAX);

        var optionalParams = new HashMap<String, Object>();
        putOptionalParamIfNotNull(DISTANCE, location.distance, optionalParams);
        putOptionalParamIfNotNull(BACK, back, optionalParams);
        putOptionalParamIfNotNull(HOTSPOT, hotspot, optionalParams);
        putOptionalParamIfNotNull(INCLUDE_PROVISIONAL, includeProvisional, optionalParams);
        putOptionalParamIfNotNull(MAX_RESULTS, maxResults, optionalParams);
        putOptionalParamIfNotNull(SPP_LOCALE, sppLocale, optionalParams);

        return getObservations(NEAREST_SPECIES,
                Map.of(
                        SPECIES_CODE, speciesCode,
                        LATITUDE, location.latitude,
                        LONGITUDE, location.longitude
                ),
                optionalParams,
                token);
    }

    private static void verifyNotNull(String name, Object value) {
        if (value == null) throw new IllegalArgumentException("%s is null".formatted(name));
    }

    private static void verifyDoubleInRange(double value, double min, double max, String name) {
        if (value < min || value > max)
            throw new IllegalArgumentException(
                    "%s is outside of range %f-%f".formatted(name, min, max));
    }

    private static void verifyIntegerInRange(String name, Integer value, int min, int max) {
        if (value != null && (value < min || value > max))
            throw new IllegalArgumentException(
                    "%s is outside of range %d-%d".formatted(name, min, max));
    }

    private static void verifyLocation(Location location) {
        verifyNotNull("location", location);
        verifyDoubleInRange(location.latitude, LATITUDE_MIN, LATITUDE_MAX, "latitude");
        verifyDoubleInRange(location.longitude,LONGITUDE_MIN, LONGITUDE_MAX, "longitude");
        verifyIntegerInRange("distance", location.distance, DISTANCE_MIN, DISTANCE_MAX);
    }

    private static void putLocationCodesIfNotEmpty(List<String> locationCodes, Map<String, Object> map) {
        if (locationCodes != null && !locationCodes.isEmpty()) map.put(LOCATION_CODES, String.join(",", locationCodes));
    }

    private static void putOptionalParamIfNotNull(String key, Object value, Map<String, Object> map) {
        if (value != null) map.put(key, value);
    }

    private List<Observation> getObservations(
            String pathTemplate,
            Map<String, Object> requiredParams,
            Map<String, Object> optionalParams,
            String token) {

        var path = build(pathTemplate, requiredParams, optionalParams);

        return client.getResourceAsListOf(path, token, Observation.class);
    }

    private static String build(String template, Map<String, Object> requiredParams, Map<String, Object> optionalParams) {
        var pathBuilder = new StringBuilder(template);

        for (var entry : requiredParams.entrySet()) {
            var token = "{{" + entry.getKey() + "}}";
            var indexOfToken = pathBuilder.indexOf(token);

            if (indexOfToken != -1) {
                pathBuilder.replace(indexOfToken, indexOfToken + token.length(), entry.getValue().toString());
            }
        }

        if (! optionalParams.isEmpty()) {
            var requestParams = new StringJoiner("&").setEmptyValue("");
            for (var entry : optionalParams.entrySet()) {
                requestParams.add(entry.getKey() + "=" + entry.getValue());
            }

            var indexOfRequestParamStartChar = pathBuilder.indexOf("?");
            if (indexOfRequestParamStartChar == -1) {
                pathBuilder.append("?");

            } else if (indexOfRequestParamStartChar != pathBuilder.length() - 1) {
                pathBuilder.append("&");
            }
            pathBuilder.append(requestParams);
        }

        return pathBuilder.toString();
    }
}