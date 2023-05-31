package cafe.app.user.controller;

import annotation.Controller;
import annotation.RequestMapping;
import http.request.HttpRequest;
import http.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.frontcontroller.Model;

import static http.common.HttpMethod.GET;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @RequestMapping(path = "/", method = GET)
    public String home(HttpRequest request, HttpResponse response, Model model) {
        return "index";
    }
}
