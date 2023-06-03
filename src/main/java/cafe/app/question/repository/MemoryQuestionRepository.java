package cafe.app.question.repository;


import cafe.app.common.pagination.Pagination;
import cafe.app.question.entity.Question;
import cafe.app.user.entity.User;
import cafe.app.user.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MemoryQuestionRepository implements QuestionRepository {

    private static final Logger logger = LoggerFactory.getLogger(MemoryQuestionRepository.class);
    private static final List<Question> store = new ArrayList<>();
    private static long sequence = 0;

    private final UserRepository userRepository;

    public MemoryQuestionRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<Question> findAll() {
        return Collections.unmodifiableList(store);
    }

    @Override
    public List<Question> findAllByPage(Pagination pagination) {
        logger.info("pagination endNumber : {}, startNumber : {}", pagination.getEndNumber(), pagination.getStartNumber());
        // 생성시간을 기준으로 내림차순 정렬
        // deleted = false 필터
        // startNumber 만큼 스킵, endNumber - startNumber 만큼 질문 가져오기
        return store.stream()
                .sorted(Comparator.comparing(Question::getCreateTime).reversed())
                .filter(question -> !question.getDeleted())
                .skip(pagination.getStartNumber() - 1)
                .limit(pagination.getEndNumber() - pagination.getStartNumber() + 1)
                .collect(Collectors.toUnmodifiableList());
    }

    @Override
    public Long findQuestionCount() {
        return (long) store.size();
    }

    @Override
    public Optional<Question> findById(Long id) {
        return store.stream()
                .filter(question -> question.getId().equals(id))
                .map(questionRowMapper())
                .findAny();
    }

    private Function<Question, Question> questionRowMapper() {
        return question -> {
            User user = userRepository.findById(question.getWriter().getId()).orElseThrow();
            return Question.builder()
                    .id(question.getId())
                    .createTime(question.getCreateTime())
                    .modifyTime(question.getModifyTime())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .deleted(question.getDeleted())
                    .writer(User.builder().id(user.getId()).name(user.getName()).build())
                    .build();
        };
    }

    @Override
    public Question save(Question question) {
        Question newQuestion =
                Question.builder()
                        .id(nextId())
                        .title(question.getTitle())
                        .content(question.getContent())
                        .createTime(LocalDateTime.now())
                        .deleted(question.getDeleted())
                        .writer(question.getWriter())
                        .build();
        store.add(newQuestion);
        logger.debug("store : {}", store);
        return newQuestion;
    }

    @Override
    public Question modify(Question question) {
        Question original = findById(question.getId()).orElseThrow();
        original.modify(question);
        return original;
    }

    @Override
    public Question deleteById(Long id) {
        Question delQuestion = findById(id).orElseThrow();
        delQuestion.delete();
        return delQuestion;
    }

    private synchronized Long nextId() {
        return ++sequence;
    }
}
