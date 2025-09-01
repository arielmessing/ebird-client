package arielmessing.ebird.api.regions;

import arielmessing.ebird.api.ApiClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionsServiceTest {

    @Mock
    private ApiClient mockApiClient;

    private RegionsService service;

    @BeforeEach
    void setUp() {
        service = new RegionsService(mockApiClient);
    }

    @Test
    void testGetRegionInfo_noQueryParams() {
        RegionInfo expected = new RegionInfo(
                "Metro Vancouver District, British Columbia, CA",
                null, null, null, 0.0, 0.0, null);

        String regionCode = "CA-BC-GV";

        when(mockApiClient.getResource(
                endsWith(regionCode),
                anyString(),
                eq(RegionInfo.class)
        )).thenReturn(expected);

        RegionInfo response = service.getRegionInfo(regionCode, null, null, "token");

        assertEquals(expected.result(), response.result());
    }

    @Test
    void testGetRegionInfo_emptyDelimiter() {
        service.getRegionInfo("regionCode", null, "", "token");

        verify(mockApiClient).getResource(
                argThat(s -> s != null && ! s.contains("delim=")),
                anyString(),
                any());

    }
}