package util.request;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Session {

    public static String sId;
    public static Map<String,Object> sessionStorage;


    public Session() {
        sessionStorage = new ConcurrentHashMap<>();
    }


    public void setSession(Object object) {
        sId = HttpRequestUtil.createSid();
        sessionStorage.put(sId,object);
    }

    public static String getSid(){
        return sId;
    }
}
