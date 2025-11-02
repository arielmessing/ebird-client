package arielmessing.ebird.api.product;

import java.util.List;

public record Checklist(
        String projId,
        String subId,
        String protocolId,
        String locId,
        Double durationHrs,
        Boolean allObsReported,
        String creationDt,
        String lastEditedDt,
        String obsDt,
        Boolean obsTimeValid,
        String checklistId,
        Integer numObservers,
        Double effortDistanceKm,
        String effortDistanceEnteredUnit,
        String subnational1Code,
        String submissionMethodCode,
        String submissionMethodVersion,
        Boolean deleteTrack,
        String userDisplayName,
        Integer numSpecies,
        String submissionMethodVersionDisp,
        List<SubAux> subAux,
        List<Object> subAuxAi,
        List<String> projectIds,
        List<Observation> obs) {

    public record SubAux(
            String subId,
            String fieldName,
            String entryMethodCode,
            String auxCode) { }

    public record Observation(
            String speciesCode,
            String exoticCategory,
            Integer howManyAtleast,
            Integer howManyAtmost,
            Boolean present,
            String howManyStr,
            String obsId,
            String comments,
            @Deprecated List<Object> hideFlags,
            @Deprecated String obsDt,
            @Deprecated String subnational1Code,
            @Deprecated String projId,
            @Deprecated String subId,
            @Deprecated Object[] obsAux) { }
}

