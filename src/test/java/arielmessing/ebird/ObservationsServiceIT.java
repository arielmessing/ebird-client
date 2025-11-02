package arielmessing.ebird;

import arielmessing.ebird.api.observations.ObservationDetail;
import arielmessing.ebird.api.observations.ObservationRankBy;
import arielmessing.ebird.api.observations.ObservationsSort;
import arielmessing.ebird.api.taxonomy.TaxonomyCategory;
import arielmessing.ebird.client.EbirdApiClient;
import arielmessing.ebird.client.EbirdApiHttpClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ObservationsServiceIT {

    private static String token;

    @Spy
    private EbirdApiClient client = new EbirdApiHttpClient();

    private ObservationsService service;

    @BeforeAll
    static void beforeAll() {
        token = System.getenv("API_KEY");
    }

    @BeforeEach
    void setUp() {
        service = new ObservationsService(client);
    }

    @Test
    void getHistoricObservationsOnDate() {
        var observations =
                service.getHistoricObservationsOnDate(
                        "GB",
                        LocalDate.now().minusDays(1),
                        null,
                        TaxonomyCategory.species,
                        true,
                        false,
                        ObservationRankBy.create,
                        ObservationDetail.full,
                        50,
                        "he",
                        token);

        assertNotNull(observations);
        System.out.println(observations.size());
    }

    @Test
    void getRecentObservationsInRegion() {
        var observations =
                service.getRecentObservationsInRegion(
                        "GB",
                        List.of("GB", "GB"),
                        ObservationsService.BACK_MAX,
                        TaxonomyCategory.species,
                        false,
                        true,
                        50,
                        "he",
                        token);

        assertNotNull(observations);
        System.out.println(observations.size());

        var argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(client).getResourceAsListOf(
                argumentCaptor.capture(),
                anyString(),
                any());

        String sent = argumentCaptor.getValue();
        System.out.println(sent);
    }

    @Test
    void getRecentNotableObservationsInRegion() {
        var observations =
                service.getRecentNotableObservationsInRegion(
                        "GB",
                        null,
                        1,
                        true,
                        ObservationDetail.full,
                        50,
                        "he",
                        token);

        assertNotNull(observations);
        System.out.println(observations.size());
    }

    @Test
    void getRecentObservationsOfSpeciesInRegion() {
        var observations =
                service.getRecentObservationsOfSpeciesInRegion(
                        "GB",
                        "hoocro1",
                        null,
                        1,
                        true,
                        true,
                        50,
                        "en",
                        token);

        assertNotNull(observations);
        System.out.println(observations.size());
    }

    @Test
    void getRecentNearbyObservations() {
        var observations =
                service.getRecentNearbyObservations(
                        new ObservationsService.Location(53.4981469, -1.0295971, 50),
                        20,
                        TaxonomyCategory.domestic,
                        false,
                        true,
                        ObservationsSort.date,
                        50,
                        "he",
                        token);

        assertNotNull(observations);
        System.out.println(observations.size());
    }

    @Test
    void getRecentNearbyNotableObservations() {
        var observations =
                service.getRecentNearbyNotableObservations(
                        new ObservationsService.Location(53.4981469, -1.0295971, 50),
                        20,
                        false,
                        ObservationDetail.full,
                        50,
                        "he",
                        token);

        assertNotNull(observations);
        System.out.println(observations.size());
    }

    @Test
    void getRecentNearbyObservationsOfSpecies() {
        var observations =
                service.getRecentNearbyObservationsOfSpecies(
                        "hoocro1",
                        new ObservationsService.Location(53.4981469, -1.0295971, 50),
                        20,
                        false,
                        true,
                        30,
                        "he",
                        token);

        assertNotNull(observations);
        System.out.println(observations.size());
    }

    @Test
    void getNearestObservationsOfSpecies() {
        var observations =
                service.getNearestObservationsOfSpecies(
                        "hoocro1",
                        new ObservationsService.Location(53.4981469, -1.0295971, 50),
                        20,
                        false,
                        true,
                        50,
                        "he",
                        token);

        assertNotNull(observations);
        System.out.println(observations.size());
    }
}