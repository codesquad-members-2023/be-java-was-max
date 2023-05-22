package http.response;

import java.util.HashMap;
import java.util.Map;

public class ResponseHeaders {
    private final String generalHeader;
    private final String responseHeader;
    private final String entityHeader;
    private final Map<String, String> temp;

    public ResponseHeaders() {
        this.generalHeader = null;
        this.responseHeader = null;
        this.entityHeader = null;
        this.temp = new HashMap<>();
    }

    public void add(String header){
        String[] splitedHeader = header.split(":", 2);
        temp.put(splitedHeader[0], splitedHeader[1].trim());
    }

    public String getGeneralHeader() {
        return generalHeader;
    }

    public String getResponseHeader() {
        return responseHeader;
    }

    public String getEntityHeader() {
        return entityHeader;
    }

    public Map<String, String> get() {
        return temp;
    }
}
