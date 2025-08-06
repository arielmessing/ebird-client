package arielmessing.ebird.api.regions;

public class RegionInfo {

    private String result;
    private RegionBounds bounds;
    private String code;
    private RegionType type;
    private double longitude;
    private double latitude;
    private RegionInfo parent;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public RegionBounds getBounds() {
        return bounds;
    }

    public void setBounds(RegionBounds bounds) {
        this.bounds = bounds;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public RegionType getType() {
        return type;
    }

    public void setType(RegionType type) {
        this.type = type;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public RegionInfo getParent() {
        return parent;
    }

    public void setParent(RegionInfo parent) {
        this.parent = parent;
    }

    @Override
    public String toString() {
        return "RegionInfo{" +
                "result='" + result + '\'' +
                ", bounds=" + bounds +
                ", code='" + code + '\'' +
                ", type=" + type +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", parent=" + parent +
                '}';
    }

    public static class RegionBounds {
        double minX;
        double maxX;
        double minY;
        double maxY;

        public double getMaxY() {
            return maxY;
        }

        public void setMaxY(double maxY) {
            this.maxY = maxY;
        }

        public double getMinY() {
            return minY;
        }

        public void setMinY(double minY) {
            this.minY = minY;
        }

        public double getMaxX() {
            return maxX;
        }

        public void setMaxX(double maxX) {
            this.maxX = maxX;
        }

        public double getMinX() {
            return minX;
        }

        public void setMinX(double minX) {
            this.minX = minX;
        }

        @Override
        public String toString() {
            return "RegionBounds{" +
                    "minX=" + minX +
                    ", maxX=" + maxX +
                    ", minY=" + minY +
                    ", maxY=" + maxY +
                    '}';
        }
    }
}
