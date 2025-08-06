package arielmessing.ebird.api.observations;

import arielmessing.ebird.api.EBirdApiClient;
import arielmessing.ebird.api.EBirdApiException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;
import java.util.List;

public class ObservationsService {

    private final EBirdApiClient client;
    private final ObjectMapper objectMapper;

    public ObservationsService(EBirdApiClient client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    public List<Observation> getHistoricObservationsOnDate(String regionCode, LocalDate date) throws EBirdApiException {
        String responseJson =
                client.getResource(
                        "data/obs/" +
                                regionCode +
                                "/historic/" +
                                date.getYear() +
                                "/" +
                                date.getMonthValue() +
                                "/" +
                                date.getDayOfMonth() +
                                "/");

        try {
            return objectMapper.readValue(responseJson, new TypeReference<>() {});

        } catch (Exception e) {
            throw new EBirdApiException("Error parsing response", e);
        }
    }
}
