package arielmessing.ebird.api.taxonomy;

import arielmessing.ebird.api.ApiClient;
import arielmessing.ebird.api.ApiException;

import java.util.Arrays;
import java.util.List;

public class TaxonomyService {
    private static final String NO_TOKEN = "";

    private final ApiClient client;

    public TaxonomyService(ApiClient client) {
        this.client = client;
    }

    public List<TaxonomyEntry> getEBirdTaxonomy(
            TaxonomyCategory category,
            List<String> species,
            String version,
            String locale) throws ApiException {

        StringBuilder sb = new StringBuilder("ref/taxonomy/ebird?fmt=json");
        if (category != null) sb.append("&cat=").append(category);
        if (species != null && ! species.isEmpty()) sb.append("&species=").append(String.join(",", species));
        if (version != null) sb.append("&version=").append(version);
        if (locale != null) sb.append("&locale=").append(locale);

        return Arrays.asList(client.getResource(sb.toString(), NO_TOKEN, TaxonomyEntry[].class));
    }

    public List<String> getTaxonomicForms(String speciesCode, String token) throws ApiException {
        return Arrays.asList(client.getResource(
                "ref/taxon/forms/" + speciesCode,
                token,
                String[].class));
    }

    public List<TaxaLocaleCode> getTaxaLocaleCodes(String token) throws ApiException {
        return Arrays.asList(client.getResource(
                "ref/taxa-locales/ebird",
                token,
                TaxaLocaleCode[].class));
    }

    public List<TaxonomyVersion> getTaxonomyVersions() throws ApiException {
        return Arrays.asList(client.getResource(
                "ref/taxonomy/versions",
                NO_TOKEN,
                TaxonomyVersion[].class));
    }

    public List<TaxonomicGroup> getTaxonomicGroups(SpeciesGrouping speciesGrouping, String groupNameLocale, String token) throws ApiException {
        return Arrays.asList(client.getResource(
                "ref/sppgroup/%s?groupNameLocale=%s".formatted(speciesGrouping, groupNameLocale),
                token,
                TaxonomicGroup[].class));
    }
}
