package arielmessing.ebird;

import arielmessing.ebird.api.product.RankedBy;
import arielmessing.ebird.api.product.SortKey;
import arielmessing.ebird.client.EbirdApiHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

class ProductServiceIT {

    private static String token;

    private static ProductService service;

    @BeforeAll
    static void beforeAll() {
        token = System.getenv("API_KEY");

        service = new ProductService(new EbirdApiHttpClient());
    }

    @Test
    void getTop100Contributors() {
        service.getTop100Contributors("IL", LocalDate.now(), RankedBy.SPECIES_SEEN, 10, token);
    }

    @Test
    void getRecentChecklistsFeed() {
        service.getRecentChecklistsFeed("IL", 10, token);
    }

    @Test
    void getChecklistsFeedOnDate() {
        service.getChecklistsFeedOnDate("IL", LocalDate.now(), SortKey.obs_dt, 10, token);
    }

    @Test
    void getRegionalStatisticsOnDate() {
        service.getRegionalStatisticsOnDate("IL", LocalDate.now(), token);
    }

    @Test
    void getSpeciesListForRegion() {
        service.getSpeciesListForRegion("IL", token);
    }

    @Test
    void getChecklist() {
        service.getChecklist("S282568434", token);
    }
}