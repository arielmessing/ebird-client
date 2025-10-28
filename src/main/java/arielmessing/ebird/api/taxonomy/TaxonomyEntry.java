package arielmessing.ebird.api.taxonomy;

import java.util.List;

public record TaxonomyEntry(
     String sciName,
     String comName,
     String speciesCode,
     String category,
     double taxonOrder,
     List<String> bandingCodes,
     List<String> comNameCodes,
     List<String> sciNameCodes,
     String order,
     String familyCode,
     String familyComName,
     String familySciName,
     String reportAs,
     boolean extinct,
     String extinctYear) {

    public TaxonomyEntry {
        bandingCodes = List.copyOf(bandingCodes);
        comNameCodes = List.copyOf(comNameCodes);
        sciNameCodes = List.copyOf(sciNameCodes);
    }
}
