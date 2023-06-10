package controller.articlecontroller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import annotation.MethodType;
import annotation.RequestMapping;
import controller.Controller;
import db.ArticleRepository;
import model.Article;
import request.HttpRequest;

@RequestMapping("/articles")
public class ArticlePostController implements Controller {

	Logger logger = LoggerFactory.getLogger(ArticlePostController.class);
	ArticleRepository articleRepository;

	public ArticlePostController() {
		this.articleRepository = new ArticleRepository();
	}

	@Override
	@MethodType("POST")
	public String process(HttpRequest request) {
		Article article = new Article(request.getParameters().get("title"), request.getParameters().get("content"));
		articleRepository.save(article);
		logger.info("saved article {}", articleRepository.findArticleById(article.getId()));
		return "redirect:/";
	}
}
