package webserver.frontcontroller.controller;

import java.util.Map;
import webserver.frontcontroller.ModelAndView;

public interface ModelAndViewController {

    ModelAndView process(Map<String, String> paramMap);
}
