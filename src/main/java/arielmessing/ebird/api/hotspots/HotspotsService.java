package arielmessing.ebird.api.hotspots;

import arielmessing.ebird.api.ApiClient;

import java.util.Arrays;
import java.util.List;

public class HotspotsService {

    static final int HIR_BACK_MIN_VAL = 1;
    static final int HIR_BACK_MAX_VAL = 30;

    static final double NH_LAT_MIN_VAL = -90.0;
    static final double NH_LAT_MAX_VAL = 90.0;
    static final double NH_LNG_MIN_VAL = -180.0;
    static final double NH_LNG_MAX_VAL = 180.0;
    static final int NH_BACK_MIN_VAL = 1;
    static final int NH_BACK_MAX_VAL = 30;
    static final int NH_DIST_MIN_VAL = 0;
    static final int NH_DIST_MAX_VAL = 500;

    private final ApiClient client;

    public HotspotsService(ApiClient client) {
        this.client = client;
    }

    public List<Hotspot> getHotspotsInRegion(String regionCode, Integer back, String token) {
        if (back != null && (back <= HIR_BACK_MIN_VAL || back >= HIR_BACK_MAX_VAL))
            throw new IllegalArgumentException("back out of range: " + back);

        StringBuilder sb = new StringBuilder("ref/hotspot/").append(regionCode).append("?fmt=json");
        if (back != null) sb.append("&back=").append(back);

        return Arrays.asList(client.getResource(sb.toString(), token, Hotspot[].class));
    }

    public HotspotInfo getHotspotInfo(String locationCode, String token) {
        return client.getResource(
                "ref/hotspot/info/" + locationCode,
                token,
                HotspotInfo.class);
    }

    public List<Hotspot> getNearbyHotspots(
            double latitude,
            double longitude,
            Integer back,
            Integer distance,
            String token) {

        if (latitude < NH_LAT_MIN_VAL || latitude > NH_LAT_MAX_VAL)
            throw new IllegalArgumentException("latitude out of range: " + latitude);

        if (longitude < NH_LNG_MIN_VAL || longitude > NH_LNG_MAX_VAL)
            throw new IllegalArgumentException("longitude out of range: " + longitude);

        if (back != null && (back <= NH_BACK_MIN_VAL || back >= NH_BACK_MAX_VAL))
            throw new IllegalArgumentException("back out of range: " + back);

        if (distance != null && (distance <= NH_DIST_MIN_VAL || distance >= NH_DIST_MAX_VAL))
            throw new IllegalArgumentException("distance out of range: " + distance);

        StringBuilder sb = new StringBuilder("ref/hotspot/geo?fmt=json")
                .append("&lat=").append(latitude)
                .append("&lng=").append(longitude);

        if (back != null) sb.append("&back=").append(back);
        if (distance != null) sb.append("&dist=").append(distance);

        return Arrays.asList(client.getResource(sb.toString(), token, Hotspot[].class));
    }
}
