package arielmessing.ebird;

import arielmessing.ebird.api.observations.Observation;
import arielmessing.ebird.client.EbirdApiClient;

import java.time.LocalDate;
import java.util.List;

public final class ObservationsService {

    private final EbirdApiClient client;

    public ObservationsService(EbirdApiClient client) {
        this.client = client;
    }

    public List<Observation> getHistoricObservationsOnDate(
            String regionCode,
            LocalDate date,
            String token) {

        return client.getResourceAsListOf(
                        "data/obs/" +
                                regionCode +
                                "/historic/" +
                                date.getYear() +
                                "/" +
                                date.getMonthValue() +
                                "/" +
                                date.getDayOfMonth() +
                                "/",
                token,
                Observation.class);
    }
}
