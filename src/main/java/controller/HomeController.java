package controller;

import java.util.List;

import annotation.MethodType;
import annotation.RequestMapping;
import db.ArticleRepository;
import model.Article;
import request.HttpRequest;

@RequestMapping("/")
public class HomeController implements Controller {
	ArticleRepository articleRepository;

	public HomeController() {
		this.articleRepository = new ArticleRepository();
	}

	@Override
	@MethodType("GET")
	public String process(HttpRequest request) {
		List<Article> articles = articleRepository.findAll();
		request.setModelAttribute("articles", articles);
		return "/index.html";
	}
}
