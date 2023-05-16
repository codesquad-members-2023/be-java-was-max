package request;

import java.util.Map;

public class QueryString {
    private final Map<String, String> queryStrings;

    public QueryString(String queryString) {
        this.queryStrings = HttpRequestUtils.parseQueryString(queryString);
    }

    public String findValueByKey(String key) {
        return queryStrings.get(key);
    }

    @Override
    public String toString() {
        return "QueryString [" + queryStrings.entrySet() + "]";
    }
}
