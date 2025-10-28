package arielmessing.ebird.client.taxonomy;

import arielmessing.ebird.client.EbirdClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.http.HttpClient;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

class TaxonomyServiceIT {

    private static String token;

    private static TaxonomyService service;

    @BeforeAll
    static void globalSetUp() {
        token = System.getenv("API_KEY");

        EbirdClient client = new EbirdClient(HttpClient.newBuilder().build(), new ObjectMapper());
        service = new TaxonomyService(client);
    }

    @Test
    void getEBirdTaxonomy() {
        var rockPigeon = "rocpig1";

        List<TaxonomyEntry> taxonomy =
                service.getEBirdTaxonomy(
                        TaxonomyCategory.domestic,
                        List.of(rockPigeon),
                        "2024",
                        "he");

        assertEquals(1, taxonomy.size());
        assertEquals(rockPigeon, taxonomy.getFirst().speciesCode());
    }

    @Test
    void getTaxonomicForms() {
        service.getTaxonomicForms("virrie", token);
    }

    @Test
    void getTaxaLocaleCodes() {
        List<TaxaLocaleCode> codes = service.getTaxaLocaleCodes(token);
        assertFalse(codes.isEmpty());
    }

    @Test
    void getTaxonomyVersions() {
        List<TaxonomyVersion> versions = service.getTaxonomyVersions();
        assertFalse(versions.isEmpty());
    }

    @Test
    void getTaxonomicGroups() {
        List<TaxonomicGroup> groups = service.getTaxonomicGroups(SpeciesGrouping.ebird, null);
        assertFalse(groups.isEmpty());
    }
}