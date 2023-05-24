package service;

import model.Request;
import util.RequestParser;

public class MainService {

    private final Service service;

    public MainService(Request request) {
        String url = request.getRequestLine().getUrl();
        String[] parsedUrl = RequestParser.parseUrl(url);

        switch (parsedUrl[1]) {
            case "user":
                service = new UserService(request);
                return;
        }
        service = null;
    }

    public void serve() {
        if (service != null) {
            service.serve();
        }
    }
}
