package arielmessing.ebird;

import arielmessing.ebird.api.taxonomy.*;
import arielmessing.ebird.client.EbirdApiClient;

import java.util.List;

public final class TaxonomyService {
    private static final String NO_TOKEN = "";

    private final EbirdApiClient client;

    public TaxonomyService(EbirdApiClient client) {
        this.client = client;
    }

    public List<TaxonomyEntry> getEBirdTaxonomy(
            TaxonomyCategory category,
            List<String> species,
            String version,
            String locale) {

        StringBuilder sb = new StringBuilder("ref/taxonomy/ebird?fmt=json");
        if (category != null) sb.append("&cat=").append(category);
        if (species != null && ! species.isEmpty()) sb.append("&species=").append(String.join(",", species));
        if (version != null) sb.append("&version=").append(version);
        if (locale != null) sb.append("&locale=").append(locale);

        return client.getResourceAsListOf(sb.toString(), NO_TOKEN, TaxonomyEntry.class);
    }

    public List<String> getTaxonomicForms(String speciesCode, String token) {
        return client.getResourceAsListOf(
                "ref/taxon/forms/" + speciesCode,
                token,
                String.class);
    }

    public List<TaxaLocaleCode> getTaxaLocaleCodes(String token) {
        return client.getResourceAsListOf(
                "ref/taxa-locales/ebird",
                token,
                TaxaLocaleCode.class);
    }

    public List<TaxonomyVersion> getTaxonomyVersions() {
        return client.getResourceAsListOf(
                "ref/taxonomy/versions",
                NO_TOKEN,
                TaxonomyVersion.class);
    }

    public List<TaxonomicGroup> getTaxonomicGroups(
            SpeciesGrouping speciesGrouping,
            GroupNameLocale groupNameLocale) {

        StringBuilder sb = new StringBuilder("ref/sppgroup/").append(speciesGrouping);
        if (groupNameLocale != null) sb.append("?groupNameLocale=").append(groupNameLocale);

        return client.getResourceAsListOf(sb.toString(), NO_TOKEN, TaxonomicGroup.class);
    }
}
