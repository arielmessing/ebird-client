package arielmessing.ebird.api.hotspots;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record HotspotInfo(
        String locId,
        @JsonAlias("locName") String name,
        @JsonAlias("lat") double latitude,
        @JsonAlias("lng") double longitude,
        String countryCode,
        String countryName,
        String subnational1Name,
        String subnational1Code,
        String subnational2Name,
        String subnational2Code,
        boolean isHotspot,
        String hierarchicalName,
        @Deprecated String locID) {}