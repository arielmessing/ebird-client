package arielmessing.ebird.api.hotspots;

public class Hotspot {

    private String locId;
    private String locName;
    private String countryCode;
    private String subnational1Code;
    private double lat;
    private double lng;
    private String latestObsDt;
    private int numSpeciesAllTime;

    public String getLocId() {
        return locId;
    }

    public void setLocId(String locId) {
        this.locId = locId;
    }

    public String getLocName() {
        return locName;
    }

    public void setLocName(String locName) {
        this.locName = locName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getSubnational1Code() {
        return subnational1Code;
    }

    public void setSubnational1Code(String subnational1Code) {
        this.subnational1Code = subnational1Code;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getLatestObsDt() {
        return latestObsDt;
    }

    public void setLatestObsDt(String latestObsDt) {
        this.latestObsDt = latestObsDt;
    }

    public int getNumSpeciesAllTime() {
        return numSpeciesAllTime;
    }

    public void setNumSpeciesAllTime(int numSpeciesAllTime) {
        this.numSpeciesAllTime = numSpeciesAllTime;
    }

    @Override
    public String toString() {
        return "Location{" +
                "locId='" + locId + '\'' +
                ", locName='" + locName + '\'' +
                ", countryCode='" + countryCode + '\'' +
                ", subnational1Code='" + subnational1Code + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", latestObsDt='" + latestObsDt + '\'' +
                ", numSpeciesAllTime=" + numSpeciesAllTime +
                '}';
    }
}
