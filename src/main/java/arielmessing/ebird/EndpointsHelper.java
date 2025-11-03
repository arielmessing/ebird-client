package arielmessing.ebird;

import java.util.Map;
import java.util.StringJoiner;

public final class EndpointsHelper {

    public static String build(String template, Map<String, Object> requiredParams, Map<String, Object> optionalParams) {
        var pathBuilder = new StringBuilder(template);

        for (var entry : requiredParams.entrySet()) {
            var token = "{{" + entry.getKey() + "}}";
            var indexOfToken = pathBuilder.indexOf(token);

            if (indexOfToken != -1) {
                pathBuilder.replace(indexOfToken, indexOfToken + token.length(), entry.getValue().toString());
            }
        }

        if (! optionalParams.isEmpty()) {
            var requestParams = new StringJoiner("&").setEmptyValue("");
            for (var entry : optionalParams.entrySet()) {
                requestParams.add(entry.getKey() + "=" + entry.getValue());
            }

            var indexOfRequestParamStartChar = pathBuilder.indexOf("?");
            if (indexOfRequestParamStartChar == -1) {
                pathBuilder.append("?");

            } else if (indexOfRequestParamStartChar != pathBuilder.length() - 1) {
                pathBuilder.append("&");
            }
            pathBuilder.append(requestParams);
        }

        return pathBuilder.toString();
    }
}
