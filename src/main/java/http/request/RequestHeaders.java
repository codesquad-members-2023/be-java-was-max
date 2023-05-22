package http.request;

import http.ContentType;
import http.request.headers.EntityHeader;
import http.request.headers.GeneralHeader;
import http.request.headers.RequestHeader;

import java.util.HashMap;
import java.util.Map;

public class RequestHeaders {
    private final GeneralHeader generalHeader;
    private final RequestHeader requestHeader;
    private final EntityHeader entityHeader;
    private final Map<String, String> temp;

    public RequestHeaders() {
        this.generalHeader = new GeneralHeader();
        this.requestHeader = new RequestHeader();
        this.entityHeader = new EntityHeader();
        this.temp = new HashMap<>();
    }

    // TODO: 적절한 header를 판단해서 Data를 넣어주는 역할.
    public void add(String header){
        String[] splitedHeader = header.split(":", 2);
        temp.put(splitedHeader[0], splitedHeader[1].trim());
    }

    public String get(String name){
        return temp.get(name);
    }

    public GeneralHeader getGeneralHeader() {
        return generalHeader;
    }

    public RequestHeader getRequestHeader() {
        return requestHeader;
    }

    public EntityHeader getEntityHeader() {
        return entityHeader;
    }

    public Map<String, String> getTemp() {
        return temp;
    }

    public String getContentType(String uri){
        return ContentType.get(uri);
    }

    public Map<String, String> get(){
        return temp;
    }
}
