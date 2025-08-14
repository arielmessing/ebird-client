package arielmessing.ebird.api.taxonomy;

import arielmessing.ebird.api.ApiException;
import arielmessing.ebird.api.ApiHttpClient;
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

        ApiHttpClient client =  new ApiHttpClient(HttpClient.newBuilder().build(), new ObjectMapper());
        service = new TaxonomyService(client);
    }

    @Test
    void getEBirdTaxonomy() throws ApiException {
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
    void getTaxonomicForms() throws ApiException {
        service.getTaxonomicForms("virrie", token);
    }

    @Test
    void getTaxaLocaleCodes() throws ApiException {
        List<TaxaLocaleCode> codes = service.getTaxaLocaleCodes(token);
        assertFalse(codes.isEmpty());
    }

    @Test
    void getTaxonomyVersions() throws ApiException {
        List<TaxonomyVersion> versions = service.getTaxonomyVersions();
        assertFalse(versions.isEmpty());
    }

    @Test
    void getTaxonomicGroups() throws ApiException {
        List<TaxonomicGroup> groups = service.getTaxonomicGroups(SpeciesGrouping.ebird, "en", token);
        assertFalse(groups.isEmpty());
    }
}