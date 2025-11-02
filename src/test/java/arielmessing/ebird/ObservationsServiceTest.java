package arielmessing.ebird;

import arielmessing.ebird.api.taxonomy.TaxonomyCategory;
import arielmessing.ebird.client.EbirdApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static arielmessing.ebird.ObservationsService.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ObservationsServiceTest {

    static Stream<Arguments> locationCodesProvider() {
        return Stream.of(
                Arguments.of(null, false),
                Arguments.of(List.of(), false),
                Arguments.of(List.of("foo"), true),
                Arguments.of(List.of("bar", "baz"), true)
        );
    }

    @Mock
    private EbirdApiClient client;

    private ObservationsService service;

    @BeforeEach
    void setUp() {
        service = new ObservationsService(client);
    }

    @Test
    void getRecentObservationsInRegion_whenNullRegionCode_throwsException() {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.getRecentObservationsInRegion(null, null, null, null, null, null, null, null, "token")
        );

        assertTrue(ex.getMessage().contains(REGION_CODE));
    }

    @ParameterizedTest
    @ValueSource(ints = { BACK_MIN - 1, BACK_MAX + 1 })
    void getRecentObservationsInRegion_whenInvalidBack_throwsException(Integer back) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.getRecentObservationsInRegion("regionCode", null, back, null, null, null, null, null, "token")
        );

        assertTrue(ex.getMessage().contains(BACK));
    }

    @ParameterizedTest
    @ValueSource(ints = { MAX_RESULTS_MIN - 1, MAX_RESULTS_MAX + 1 })
    void getRecentObservationsInRegion_whenInvalidMaxResults_throwsException(Integer maxResults) {
        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.getRecentObservationsInRegion("regionCode", null, null, null, null, null, maxResults, null, "token")
        );

        assertTrue(ex.getMessage().contains(MAX_RESULTS));
    }

    @ParameterizedTest
    @CsvSource({
            ", false",
            "form, true"
    })
    void getRecentObservationsInRegion_whenNoOrValidCategory_makesRequest(TaxonomyCategory category, boolean expected) {
        service.getRecentObservationsInRegion("regionCode", null, null, category, null, null, null, null, "token");

        verify(client).getResourceAsListOf(
                argThat(resourcePath -> expected == resourcePath.contains("cat=")),
                anyString(),
                any());
    }

    @ParameterizedTest
    @MethodSource("locationCodesProvider")
    void getRecentObservationsInRegion_whenNoOrValidLocationCodes_makesRequest(List<String> locationCodes, boolean expected) {
        service.getRecentObservationsInRegion("regionCode", locationCodes, null, null, null, null, null, null, "token");

        var argumentCaptor = ArgumentCaptor.forClass(String.class);
        verify(client).getResourceAsListOf(argumentCaptor.capture(), anyString(), any());

        var resourcePath = argumentCaptor.getValue();
        System.out.println(resourcePath);
        assertEquals(expected, resourcePath.contains("r="));
        if (expected) {
            var commas = IntStream.range(resourcePath.indexOf('?') + 1, resourcePath.length())
                    .map(resourcePath::charAt)
                    .filter(c -> c == ',')
                    .count();

            assertEquals(locationCodes.size() - 1, commas);
        }
    }
}