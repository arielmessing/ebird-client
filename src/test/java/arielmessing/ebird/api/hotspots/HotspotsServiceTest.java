package arielmessing.ebird.api.hotspots;

import arielmessing.ebird.api.ApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static arielmessing.ebird.api.hotspots.HotspotsService.*;
import static arielmessing.ebird.api.hotspots.HotspotsService.NH_LAT_MAX_VAL;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class HotspotsServiceTest {

    @Mock
    private ApiClient mockApiClient;

    private HotspotsService service;

    @BeforeEach
    void setUp() {
        service = new HotspotsService(mockApiClient);
    }

    @Test
    void getHotspotsInRegion_validations() {
        var back = HIR_BACK_MAX_VAL + 1;

        IllegalArgumentException ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.getHotspotsInRegion("region", back, "token")
        );

        assertEquals("back out of range: " + back, ex.getMessage());
    }

    @Test
    void getNearbyHotspots_validations() {
        var lat = NH_LAT_MIN_VAL - 1;

        var ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.getNearbyHotspots(lat, NH_LNG_MIN_VAL, null, null,"token")
        );

        assertEquals("latitude out of range: " + lat, ex.getMessage());

        var lng = NH_LNG_MAX_VAL + 1;

        ex = assertThrows(
                IllegalArgumentException.class,
                () -> service.getNearbyHotspots(NH_LAT_MAX_VAL, lng, null, null,"token")
        );

        assertEquals("longitude out of range: " + lng, ex.getMessage());
    }
}