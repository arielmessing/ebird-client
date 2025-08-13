package arielmessing.ebird.api.regions;

import arielmessing.ebird.api.ApiClient;
import arielmessing.ebird.api.ApiException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionsServiceTest {

    @Mock
    private ApiClient mockClient;

    @Test
    void testGetRegionInfo_NoQueryParams() throws IOException, InterruptedException, ApiException {
        RegionInfo expected = new RegionInfo();
        expected.setResult("Metro Vancouver District, British Columbia, CA");

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
                    token);

        assertEquals(expected.getResult(), response.getResult());
    }
}