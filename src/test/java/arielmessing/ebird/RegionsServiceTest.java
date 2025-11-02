package arielmessing.ebird;

import arielmessing.ebird.client.EbirdApiClient;
import arielmessing.ebird.api.regions.RegionInfo;
import arielmessing.ebird.api.regions.RegionNameFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionsServiceTest {

    @Mock
    private EbirdApiClient client;

    private RegionsService service;

    @BeforeEach
    void setUp() {
        service = new RegionsService(client);
    }

    @Test
    void testGetRegionInfo_noQueryParams() {
        RegionInfo expected = new RegionInfo(
                "Metro Vancouver District, British Columbia, CA",
                null, null, null, 0.0, 0.0, null);

        String regionCode = "CA-BC-GV";

        when(client.getResource(
                contains(regionCode),
                anyString(),
                eq(RegionInfo.class)
        )).thenReturn(expected);

        RegionInfo response = service.getRegionInfo(regionCode, null, null, "token");

        verify(client).getResource(
                argThat(s -> s != null && ! s.contains("regionNameFormat=") && ! s.contains("delim=")),
                anyString(),
                any());

        assertEquals(expected.result(), response.result());
    }

    @ParameterizedTest
    @EnumSource(RegionNameFormat.class)
    void testGetRegionInfo_emptyRegionNameFormat(RegionNameFormat regionNameFormat) {
        service.getRegionInfo("regionCode", regionNameFormat, null, "token");

        verify(client).getResource(
                contains("regionNameFormat=%s".formatted(regionNameFormat)),
                anyString(),
                any());
    }

    @Test
    void testGetRegionInfo_emptyDelimiter() {
        service.getRegionInfo("regionCode", null, "", "token");

        verify(client).getResource(
                argThat(s -> s != null && ! s.contains("delim=")),
                anyString(),
                any());
    }
}