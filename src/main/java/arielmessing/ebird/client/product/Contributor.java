package arielmessing.ebird.client.product;

public record Contributor(
        String profileHandle,
        String userDisplayName,
        int numSpecies,
        int numCompleteChecklists,
        int rowNum,
        String userId) {}
