package arielmessing.ebird.api.observations;

import arielmessing.ebird.api.ApiClient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ObservationsService {

    private final ApiClient client;

    public ObservationsService(ApiClient client) {
        this.client = client;
    }

    public List<Observation> getHistoricObservationsOnDate(
            String regionCode,
            LocalDate date,
            String token) {

        return Arrays.asList(client.getResource(
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
                Observation[].class));
    }
}
