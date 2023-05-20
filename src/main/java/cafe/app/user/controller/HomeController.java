package cafe.app.user.controller;

import static http.common.HttpMethod.GET;

import annotation.Controller;
import annotation.RequestMapping;
import cafe.app.user.controller.dto.UserResponse;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @RequestMapping(path = "/", method = GET)
    public String home(HttpRequest request, HttpResponse response) {
        HttpSession httpSession = request.getHttpSession();
        UserResponse user = (UserResponse) httpSession.getAttribute("user");
        logger.debug("httpSession : {}", httpSession);
        logger.debug("user : {}", user);
        return "index";
    }
}
