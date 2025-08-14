package arielmessing.ebird;

import arielmessing.ebird.api.ApiHttpClient;
import arielmessing.ebird.api.ApiException;
import arielmessing.ebird.api.regions.*;
import arielmessing.ebird.api.regions.RegionsService;
import arielmessing.ebird.api.taxonomy.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.http.HttpClient;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ApiException {

        String token = System.getenv("API_KEY");

        ApiHttpClient client =  new ApiHttpClient(HttpClient.newBuilder().build(), new ObjectMapper());

//        RegionsService service = new RegionsService(client);
//        RegionInfo regionInfo = service.getRegionInfo(
//                "CA",
//                RegionInfoQueryParams.builder()
//                        .regionNameFormat(RegionNameFormat.nameonly)
//                        .build(),
//                token);
//        System.out.println(regionInfo);
//
//        List<Region> countries = service.getSubRegionList(RegionType.country, "world", token);
//        for (Region country : countries) {
//            System.out.println(country.getName());
//        }

//        ProductService service = new ProductService(client);
//        List<String> species = service.getSpeciesListForRegion("IL-JM", token);
//        for (String specie : species) {
//            System.out.println(specie);
//        }

//        TaxonomyService service = new TaxonomyService(client);
//        List<TaxonomyEntry> rook1 = service.getEBirdTaxonomy(List.of("rook1"));
//        System.out.println(rook1);

//        GeographyService service = new GeographyService(client);
//        List<Region> regions = service.getAdjacentRegions("IL-D");
//        for (Region region : regions) {
//            System.out.println(region);
//        }

//        HotspotsService service = new HotspotsService(client);
//        List<Hotspot> hotspots = service.getHotspotsInRegion("IL-D");
//        for (Hotspot hotspot : hotspots) {
//            System.out.println(hotspot);
//        }

//        ObservationsService service = new ObservationsService(client);
//        List<Observation> observations = service.getHistoricObservationsOnDate("GB-ENG-KEN", LocalDate.of(2025, Month.MAY, 3));
//        for (Observation observation : observations) {
//            System.out.println(observation);
//        }
    }
}
