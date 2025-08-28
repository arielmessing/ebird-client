package arielmessing.ebird.api.hotspots;

public record HotspotInfo(
        String locId,
        String name,
        double latitude,
        double longitude,
        String countryCode,
        String countryName,
        String subnational1Name,
        String subnational1Code,
        String subnational2Name,
        String subnational2Code,
        boolean isHotspot,
        String hierarchicalName,
        @Deprecated String locID,
        @Deprecated String locName,
        @Deprecated double lat,
        @Deprecated double lng) {}