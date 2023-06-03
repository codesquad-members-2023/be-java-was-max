package cafe.app.question.repository;


import cafe.app.common.pagination.Pagination;
import cafe.app.question.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {

    List<Question> findAll();

    Optional<Question> findById(Long id);

    List<Question> findAllByPage(Pagination pagination);

    Question save(Question question);

    Question modify(Question question);

    Question deleteById(Long id);

    Long findQuestionCount();
}
