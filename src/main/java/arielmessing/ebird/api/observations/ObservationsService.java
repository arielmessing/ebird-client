package arielmessing.ebird.api.observations;

import arielmessing.ebird.api.EBirdApiClient;
import arielmessing.ebird.api.EBirdApiException;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ObservationsService {

    private final EBirdApiClient client;

    public ObservationsService(EBirdApiClient client) {
        this.client = client;
    }

    public List<Observation> getHistoricObservationsOnDate(
            String regionCode,
            LocalDate date,
            String token) throws EBirdApiException {

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
