package arielmessing.ebird.client.taxonomy;

import java.util.List;

public record TaxonomicGroup(
    String groupName,
    int groupOrder,
    List<double[]> taxonOrderBounds) {}
