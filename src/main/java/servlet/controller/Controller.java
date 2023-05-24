package servlet.controller;

import java.util.Map;

public interface Controller {

    String process(Map<String, String> parameters);
}
