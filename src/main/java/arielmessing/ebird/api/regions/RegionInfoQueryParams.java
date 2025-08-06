package arielmessing.ebird.api.regions;

public record RegionInfoQueryParams(RegionNameFormat regionNameFormat, String delim) {

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private RegionNameFormat regionNameFormat;
        private String delim;

        public Builder regionNameFormat(RegionNameFormat regionNameFormat) {
            this.regionNameFormat = regionNameFormat;
            return this;
        }

        public Builder delim(String delim) {
            this.delim = delim;
            return this;
        }

        public RegionInfoQueryParams build() {
            return new RegionInfoQueryParams(regionNameFormat, delim);
        }
    }
}
