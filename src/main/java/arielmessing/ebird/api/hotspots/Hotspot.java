package arielmessing.ebird.api.hotspots;

public record Hotspot (
    String locId,
    String locName,
    String countryCode,
    String subnational1Code,
    String subnational2Code,
    double lat,
    double lng,
    String latestObsDt,
    int numSpeciesAllTime) {}
