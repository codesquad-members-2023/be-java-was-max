package servlet.domain.request.target;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class QueryString {
    private static final String QUERY_DELIMITER = ";";
    private static final String QUERY_KEY_VALUE_DELIMITER = "=";
    private final Map<String, String> queryParamMap;

    private QueryString(Map<String, String> queryParamMap) {
        this.queryParamMap = queryParamMap;
    }

    public static QueryString of(String queryString) {
        Map<String, String> queryParamMap = new HashMap<>();
        if (isSeveralQuery(queryString)) {
            String[] query = queryString.split(QUERY_DELIMITER);
            Arrays.stream(query)
                    .forEach(eachQuery -> parseQuery(queryParamMap, eachQuery));
        } else {
            parseQuery(queryParamMap, queryString);
        }
        return new QueryString(queryParamMap);
    }

    private static boolean isSeveralQuery(String queryString) {
        return queryString.contains(QUERY_DELIMITER);
    }

    private static void parseQuery(Map<String, String> queryParamMap, String eachQuery) {
        String[] keyValue = eachQuery.split(QUERY_KEY_VALUE_DELIMITER);
        queryParamMap.put(keyValue[0], keyValue[1]);
    }

    public Map<String, String> getQueryParamMap() {
        return queryParamMap == null ? new HashMap<>() : queryParamMap;
    }

    public boolean isSameCount(int parameterCount) {
        return this.queryParamMap.size() == parameterCount;
    }


    public boolean contains(String key, String value) {
        return queryParamMap.containsKey(key) && queryParamMap.get(key).equals(value);
    }
}
