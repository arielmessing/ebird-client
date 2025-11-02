package arielmessing.ebird.api.product;

import arielmessing.ebird.api.hotspots.HotspotInfo;

public record ChecklistInFeed(
                String locId,
                String subId,
                String userDisplayName,
                Integer numSpecies,
                String obsDt,
                String obsTime,
                String isoObsDate,
                @Deprecated String subID,
                HotspotInfo loc) { }