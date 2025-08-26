package arielmessing.ebird.api.hotspots;

import arielmessing.ebird.api.ApiClient;

import java.util.Arrays;
import java.util.List;

public class HotspotsService {

    private final ApiClient client;

    public HotspotsService(ApiClient client) {
        this.client = client;
    }

    public List<Hotspot> getHotspotsInRegion(String regionCode, Integer back, String token) {
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

        StringBuilder sb = new StringBuilder("ref/hotspot/geo?fmt=json")
                .append("&lat=").append(latitude)
                .append("&lng=").append(longitude);

        if (back != null) sb.append("&back=").append(back);
        if (distance != null) sb.append("&dist=").append(distance);

        return Arrays.asList(client.getResource(sb.toString(), token, Hotspot[].class));
    }
}
