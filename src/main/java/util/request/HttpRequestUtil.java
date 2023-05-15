package util.request;

import java.util.HashMap;
import java.util.Map;

public class HttpRequestUtil {
    public static Map<String,String> paresQueryString(String queryString) {
        Map <String,String> queryParam = new HashMap<>();
        if(!queryString.equals("")) {
            String token[] = queryString.split("&");
            for(String temp : token) {
                String mapArr[] = temp.split("=");
                queryParam.put(mapArr[0],mapArr.length>1 ? mapArr[1] : "");
            }
        }
        return queryParam;
    }

}
