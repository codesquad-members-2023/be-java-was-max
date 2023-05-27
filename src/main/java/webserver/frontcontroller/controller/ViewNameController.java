package webserver.frontcontroller.controller;

import java.util.Map;

public interface ViewNameController {

    String process(Map<String, String> paramMap, Map<String, Object> model);
}
