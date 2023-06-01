package cafe.app.user.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.frontcontroller.Model;
import webserver.http.request.HttpRequest;
import webserver.http.response.HttpResponse;

import static webserver.http.common.HttpMethod.GET;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @RequestMapping(path = "/", method = GET)
    public String home(HttpRequest request, HttpResponse response, Model model) {
        return "index";
    }
}
