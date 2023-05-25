package db;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import model.Article;

public class ArticleRepository {

	private static final Map<Integer, Article> articleRepository = new LinkedHashMap();
	private int sequence = 1;

	public ArticleRepository() {
	}

	public void save(Article article) {
		article.setId(sequence++);
		articleRepository.put(sequence, article);
	}

	public List<Article> findAll() {
		return articleRepository.values().stream()
			.collect(Collectors.toUnmodifiableList());
	}

	public Optional<Article> findArticleById(int id) {
		return articleRepository.values().stream()
			.filter(article -> article.getId() == id)
			.findFirst();
	}
}
