package controller;

import http.request.HttpRequest;
import http.response.HttpResponse;

public class ArticleListController implements Controller {

    public static final String ARTICLE_LIST_PAGE = "/index.html";

    @Override
    public String process(HttpRequest request, HttpResponse response) {
        return ARTICLE_LIST_PAGE;
    }
}
