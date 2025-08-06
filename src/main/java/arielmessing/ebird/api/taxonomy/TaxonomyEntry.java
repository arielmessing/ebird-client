package arielmessing.ebird.api.taxonomy;

import java.util.List;

public class TaxonomyEntry {

    private String sciName;
    private String comName;
    private String speciesCode;
    private String category;
    private double taxonOrder;
    private List<String> bandingCodes;
    private List<String> comNameCodes;
    private List<String> sciNameCodes;
    private String order;
    private String familyCode;
    private String familyComName;
    private String familySciName;

    public String getSciName() {
        return sciName;
    }

    public void setSciName(String sciName) {
        this.sciName = sciName;
    }

    public String getComName() {
        return comName;
    }

    public void setComName(String comName) {
        this.comName = comName;
    }

    public String getSpeciesCode() {
        return speciesCode;
    }

    public void setSpeciesCode(String speciesCode) {
        this.speciesCode = speciesCode;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getTaxonOrder() {
        return taxonOrder;
    }

    public void setTaxonOrder(double taxonOrder) {
        this.taxonOrder = taxonOrder;
    }

    public List<String> getBandingCodes() {
        return bandingCodes;
    }

    public void setBandingCodes(List<String> bandingCodes) {
        this.bandingCodes = bandingCodes;
    }

    public List<String> getComNameCodes() {
        return comNameCodes;
    }

    public void setComNameCodes(List<String> comNameCodes) {
        this.comNameCodes = comNameCodes;
    }

    public List<String> getSciNameCodes() {
        return sciNameCodes;
    }

    public void setSciNameCodes(List<String> sciNameCodes) {
        this.sciNameCodes = sciNameCodes;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getFamilyCode() {
        return familyCode;
    }

    public void setFamilyCode(String familyCode) {
        this.familyCode = familyCode;
    }

    public String getFamilyComName() {
        return familyComName;
    }

    public void setFamilyComName(String familyComName) {
        this.familyComName = familyComName;
    }

    public String getFamilySciName() {
        return familySciName;
    }

    public void setFamilySciName(String familySciName) {
        this.familySciName = familySciName;
    }

    @Override
    public String toString() {
        return "TaxonomyEntry{" +
               "sciName='" + sciName + '\'' +
               ", comName='" + comName + '\'' +
               ", speciesCode='" + speciesCode + '\'' +
               ", category='" + category + '\'' +
               ", taxonOrder=" + taxonOrder +
               ", bandingCodes=" + bandingCodes +
               ", comNameCodes=" + comNameCodes +
               ", sciNameCodes=" + sciNameCodes +
               ", order='" + order + '\'' +
               ", familyCode='" + familyCode + '\'' +
               ", familyComName='" + familyComName + '\'' +
               ", familySciName='" + familySciName + '\'' +
               '}';
    }
}
