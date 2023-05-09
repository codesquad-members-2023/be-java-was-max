package util;

import model.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpRequestUtils {

    public Map<String, String> parseQueryString(String urlPostfix){
        Map<String, String> userInfo = Arrays.stream(urlPostfix.split("&"))
                .map(i -> i.split("="))
                .collect(Collectors.toMap(i -> i[0], i -> i[1]));
        replaceExclamationMark(userInfo); // 특수문자 변환
        return userInfo;
    }

    // 특수문자가 변환된 값을 다시 특수문자로 변경해준다.
    private void replaceExclamationMark(Map<String, String> userInfo){
        Map<String, String> exclamationMark = mappingExclamationMark();
        userInfo.entrySet()
                .forEach(info -> exclamationMark.forEach((trans, mark) -> {
                                    if (info.getValue().contains(trans)){ // 변형된 문자가 포함되어있으면, 원래의 특수문자로 바꿔준다.
                                        info.setValue(info.getValue().replace(trans, exclamationMark.get(trans)));
                                    }
                                }));
    }

    // userInfo에 들어있는 "!@#$%^&*()" = 0~9+= 의 특수문자에 대해서만 replace 해준다.
    // %21 %40 %23 %24 %25 %5E %26 * %28 %29
    private Map<String, String> mappingExclamationMark() {
        Map<String, String> exclamationMark = new HashMap<>();
        exclamationMark.put("%21", "!"); // 1
        exclamationMark.put("%40", "@"); // 2
        exclamationMark.put("%23", "#"); // 3
        exclamationMark.put("%24", "$"); // 4
        exclamationMark.put("%25", "%"); // 5
        exclamationMark.put("%5E", "^"); // 6
        exclamationMark.put("%26", "&"); // 7
        // 8은 *이 변환되지 않고 문자 그대로여서 추가하지 않음.
        exclamationMark.put("%28", "("); // 9
        exclamationMark.put("%29", ")"); // 0
        // _-은 변환되지 않고 문자 그대로여서 추가하지 않음.
        exclamationMark.put("%2B", "+"); // +
        exclamationMark.put("%3D", "="); // =

        return exclamationMark;
    }


    public User createUser(Map<String, String> parsedStr){
        return new User(parsedStr.get("userId"), parsedStr.get("password"), parsedStr.get("name"), parsedStr.get("email"));
    }
}
