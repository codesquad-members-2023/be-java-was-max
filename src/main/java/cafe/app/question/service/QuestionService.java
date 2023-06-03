package cafe.app.question.service;


import cafe.app.common.pagination.Pagination;
import cafe.app.question.controller.dto.QuestionResponse;
import cafe.app.question.controller.dto.QuestionSavedRequest;
import cafe.app.question.entity.Question;
import cafe.app.question.repository.QuestionRepository;
import cafe.errors.errorcode.QuestionErrorCode;
import cafe.errors.exception.ResourceNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

public class QuestionService {

    private final QuestionRepository repository;
//    private final CommentRepository commentRepository;

    public QuestionService(QuestionRepository repository) {
        this.repository = repository;
    }

    public QuestionResponse writeQuestion(QuestionSavedRequest questionRequest) {
        Question savedQuestion = repository.save(questionRequest.toEntity());
        return new QuestionResponse(savedQuestion);
    }

    public List<QuestionResponse> getAllQuestion() {
        return repository.findAll().stream()
                .map(QuestionResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<QuestionResponse> getAllQuestionByPage(Pagination pagination) {
        return repository.findAllByPage(pagination).stream()
                .map(QuestionResponse::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public QuestionResponse findQuestion(Long id) {
        Question findQuestion = repository.findById(id).orElseThrow(() -> {
            throw new ResourceNotFoundException(QuestionErrorCode.NOT_FOUND_QUESTION);
        });
        return new QuestionResponse(findQuestion);
    }

    public QuestionResponse modifyQuestion(Long id, QuestionSavedRequest questionRequest) {
        Question original = repository.findById(id).orElseThrow();
        original.modify(questionRequest.toEntity());
        return new QuestionResponse(repository.modify(original));
    }

    public QuestionResponse delete(Long id) {
//        commentRepository.deleteAllByQuestionId(id);
        return new QuestionResponse(repository.deleteById(id));
    }

    public Long getTotalData() {
        return repository.findQuestionCount();
    }
}
