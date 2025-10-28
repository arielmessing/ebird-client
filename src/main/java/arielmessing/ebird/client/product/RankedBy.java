package arielmessing.ebird.client.product;

public enum RankedBy {
    COMPLETE_CHECKLISTS("cl"),
    SPECIES_SEEN("spp");

    public final String value;

    RankedBy(String value) {
        this.value = value;
    }
}
