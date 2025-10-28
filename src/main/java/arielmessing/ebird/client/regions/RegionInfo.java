package arielmessing.ebird.client.regions;

public record RegionInfo(
    String result,
    RegionBounds bounds,
    String code,
    RegionType type,
    double longitude,
    double latitude,
    RegionInfo parent) {

    public record RegionBounds(
            double minX,
            double maxX,
            double minY,
            double maxY) {}
}






