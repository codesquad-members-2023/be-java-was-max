package container.domain.request.target;

import java.util.Map;

public class QueryString {
    private final Map<String, String> queryParamMap;

    public QueryString(Map<String, String> queryParamMap) {
        this.queryParamMap = queryParamMap;
    }

    public Map<String, String> getQueryParamMap() {
        return queryParamMap;
    }
}
