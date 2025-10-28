package arielmessing.ebird.api.observations;

public record Observation (
    String speciesCode,
    String comName,
    String sciName,
    String locId,
    String locName,
    String obsDt,
    int howMany,
    double lat,
    double lng,
    boolean obsValid,
    boolean obsReviewed,
    boolean locationPrivate,
    String subId,
    String exoticCategory) { }
