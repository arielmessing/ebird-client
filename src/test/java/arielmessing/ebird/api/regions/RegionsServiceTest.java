package arielmessing.ebird.api.regions;

import arielmessing.ebird.api.ApiClient;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionsServiceTest {

    @Mock
    private ApiClient mockClient;

    @Test
    void testGetRegionInfo_NoQueryParams() {
        RegionInfo expected = new RegionInfo(
                "Metro Vancouver District, British Columbia, CA",
                null, null, null, 0.0, 0.0, null);

        String regionCode = "CA-BC-GV";
        String token = "api-token";

        when(mockClient.getResource(
                endsWith(regionCode),
                eq(token),
                eq(RegionInfo.class)
        )).thenReturn(expected);

        RegionInfo response =
                new RegionsService(mockClient).getRegionInfo(
                    regionCode,
                    null,
                    null,
                    token);

        assertEquals(expected.result(), response.result());
    }
}