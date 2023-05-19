package cafe.app.user.controller;

import static http.common.HttpMethod.GET;
import static http.common.header.RequestHeaderType.COOKIE;

import annotation.Controller;
import annotation.RequestMapping;
import http.request.HttpRequest;
import http.response.HttpResponse;
import http.session.Cookie;
import http.session.SessionContainer;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @RequestMapping(path = "/", method = GET)
    public String home(HttpRequest request, HttpResponse response) {
        String cookieString = request.getRequestHeader().get(COOKIE).orElse(null);
        List<Cookie> cookies = Cookie.parse(cookieString);
        Cookie sidCookie = cookies.stream().filter(cookie -> cookie.getName().equals("sid")).findAny().orElse(null);

        if (sidCookie != null) {
            logger.debug("세션 존재 여부 : {}", SessionContainer.containsHttpSession(sidCookie.getValue()));
        }

        return "index";
    }
}
