package cafe.app.question.controller.dto;

import cafe.app.question.entity.Question;
import cafe.app.user.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class QuestionSavedRequest {

    private static final Logger log = LoggerFactory.getLogger(QuestionSavedRequest.class);

    private String title;
    private String content;
    private Long userId;

    public QuestionSavedRequest() {
    }

    public QuestionSavedRequest(String title, String content, Long userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }

    public Question toEntity() {
        return Question.builder()
                .title(title)
                .content(content)
                .writer(User.builder().id(userId).build())
                .deleted(Boolean.FALSE)
                .build();
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Long getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "QuestionSavedRequest{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                '}';
    }
}
