package arielmessing.ebird.api.regions;

import java.util.HashMap;
import java.util.Map;

import static arielmessing.ebird.EndpointsHelper.build;

public class RegionsEndpoints {

    static final String REGION_INFO = "ref/region/info/{{regionCode}}";

    static final String SUB_REGION_LIST = "ref/region/list/{{regionType}}/{{parentRegionCode}}";

    public static String regionInfo(
            String regionCode,
            RegionNameFormat regionNameFormat,
            String delim) {

        var optionalParams = new HashMap<String, Object>();
        if (regionNameFormat != null) optionalParams.put("regionNameFormat", regionNameFormat);
        if (delim != null && ! delim.isEmpty()) optionalParams.put("delim", delim);

        return build(REGION_INFO, Map.of("regionCode", regionCode), optionalParams);
    }

    public static String subRegionList(
            RegionType subRegionType,
            String parentRegionCode) {

        return build(SUB_REGION_LIST,
                Map.of(
                        "regionType", subRegionType,
                        "parentRegionCode", parentRegionCode
                ),
                Map.of("fmt", "json"));
    }
}
