package arielmessing.ebird.client.observations;

import arielmessing.ebird.client.EbirdClient;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class ObservationsService {

    private final EbirdClient client;

    public ObservationsService(EbirdClient client) {
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
