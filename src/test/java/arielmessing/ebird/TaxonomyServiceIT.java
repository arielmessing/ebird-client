package arielmessing.ebird;

import arielmessing.ebird.api.taxonomy.*;
import arielmessing.ebird.client.EbirdApiHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TaxonomyServiceIT {

    private static String token;

    private static TaxonomyService service;

    @BeforeAll
    static void beforeAll() {
        token = System.getenv("API_KEY");

        service = new TaxonomyService(new EbirdApiHttpClient());
    }

    @Test
    void getEBirdTaxonomy() {
        var rockPigeon = "rocpig1";

        var taxonomy = service.getEBirdTaxonomy(
                        TaxonomyCategory.domestic,
                        List.of(rockPigeon),
                        "2024",
                        "he");

        assertEquals(1, taxonomy.size());
        assertEquals(rockPigeon, taxonomy.getFirst().speciesCode());
    }

    @Test
    void getTaxonomicForms() {
        var taxonomicForms = service.getTaxonomicForms("virrie", token);
        assertFalse(taxonomicForms.isEmpty());
    }

    @Test
    void getTaxaLocaleCodes() {
        var codes = service.getTaxaLocaleCodes(token);
        assertFalse(codes.isEmpty());
    }

    @Test
    void getTaxonomyVersions() {
        var versions = service.getTaxonomyVersions();
        assertFalse(versions.isEmpty());
    }

    @Test
    void getTaxonomicGroups() {
        var groups = service.getTaxonomicGroups(SpeciesGrouping.ebird, null);
        assertFalse(groups.isEmpty());
    }
}