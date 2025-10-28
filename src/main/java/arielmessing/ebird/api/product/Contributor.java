package arielmessing.ebird.api.product;

public record Contributor(
        String profileHandle,
        String userDisplayName,
        int numSpecies,
        int numCompleteChecklists,
        int rowNum,
        String userId) {}
