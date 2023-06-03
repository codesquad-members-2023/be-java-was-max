package cafe.app.question.controller;


import cafe.app.common.pagination.Pagination;
import cafe.app.question.controller.dto.QuestionResponse;
import cafe.app.question.controller.dto.QuestionSavedRequest;
import cafe.app.question.service.QuestionService;
import cafe.app.user.controller.dto.UserResponse;
import cafe.errors.errorcode.UserErrorCode;
import cafe.errors.exception.RestApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import webserver.annotation.Controller;
import webserver.annotation.RequestMapping;
import webserver.frontcontroller.Model;
import webserver.http.request.HttpRequest;
import webserver.http.request.component.RequestMessageBody;
import webserver.http.response.HttpResponse;
import webserver.http.session.HttpSession;

import java.util.List;

import static webserver.http.common.HttpMethod.*;


@Controller
public class QuestionController {

    private static final Logger logger = LoggerFactory.getLogger(QuestionController.class);
    private final QuestionService questionService;
//    private final CommentService commentService;

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping(path = "/", method = GET)
    public String listQuestion(HttpRequest request, HttpResponse response, Model model) {
        String page = request.getQueryString().get("page");
        logger.debug("page : {}", page);
        Long totalData = questionService.getTotalData();
        Long currentPage = parsePageNumber(page);
        Pagination pagination = new Pagination(totalData, currentPage);
        List<QuestionResponse> questions = questionService.getAllQuestionByPage(pagination);
        model.addAttribute("questions", questions);
        model.addAttribute("pagination", pagination);
        logger.debug("pagination : {}", pagination);
        return "index";
    }

    private Long parsePageNumber(String page) {
        try {
            long currentPage = Long.parseLong(page);
            return Math.max(currentPage, 1);
        } catch (NumberFormatException e) {
            return 1L;
        }
    }

    @RequestMapping(path = "/qna", method = POST)
    public String addQuestion(HttpRequest request, HttpResponse response, Model model) {
        RequestMessageBody messageBody = request.getMessageBody();
        String title = messageBody.get("title");
        String content = messageBody.get("content");
        Long userId = Long.valueOf(messageBody.get("userId"));
        QuestionSavedRequest questionRequest = new QuestionSavedRequest(title, content, userId);
        logger.debug("questionRequest : {}", questionRequest);

        model.addAttribute("user", questionService.writeQuestion(questionRequest));
        return "redirect:/";
    }

    @RequestMapping(path = "/qna/detail", method = GET)
    public String detailQuestion(HttpRequest request, HttpResponse response, Model model) {
        if (!request.hasHttpSession()) {
            return "redirect:/user/login";
        }

        long id = Long.parseLong(request.getQueryString().get("id"));
        long cursor = 0L;
        if (request.getQueryString().get("cursor") != null) {
            cursor = Long.parseLong(request.getQueryString().get("cursor"));
        }
        logger.debug("cursor : {}", cursor);

        model.addAttribute("question", questionService.findQuestion(id));
        return "qna/detail";
    }

    @RequestMapping(path = "/qna/{id}", method = PUT)
    public String modifyQuestion(HttpRequest request, HttpResponse response, Model model) {
        // Long id, QuestionSavedRequest, HttpSession
        RequestMessageBody messageBody = request.getMessageBody();
        Long id = Long.valueOf(messageBody.get("id"));
        String title = messageBody.get("title");
        String content = messageBody.get("content");
        Long userId = Long.valueOf(messageBody.get("userId"));

        QuestionSavedRequest questionRequest = new QuestionSavedRequest(title, content, userId);
        HttpSession session = request.getHttpSession();

        UserResponse user = (UserResponse) session.getAttribute("user");
        QuestionResponse question = questionService.findQuestion(id);
        if (!question.getUserId().equals(user.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        model.addAttribute("user", questionService.modifyQuestion(id, questionRequest));
        return String.format("redirect:qna/%d", id);
    }

    @RequestMapping(path = "/qna/{id}", method = DELETE)
    public String deleteQuestion(HttpRequest request, HttpResponse response, Model model) {
        RequestMessageBody messageBody = request.getMessageBody();
        Long id = Long.valueOf(messageBody.get("id"));
        HttpSession session = request.getHttpSession();
        logger.debug("id : {}", id);

        UserResponse loggerinUser = (UserResponse) session.getAttribute("user");
        QuestionResponse question = questionService.findQuestion(id);
        if (!question.getUserId().equals(loggerinUser.getId())) {
            throw new RestApiException(UserErrorCode.PERMISSION_DENIED);
        }

        model.addAttribute("user", questionService.delete(id));
        return "redirect:index";
    }

    @RequestMapping(path = "/qna/new", method = GET)
    public String addQuestionForm(HttpRequest request, HttpResponse response, Model model) {
        // 로그인 하지 않은 경우 로그인 페이지로 이동
        if (!request.hasHttpSession()) {
            return "redirect:/login";
        }
        return "qna/new";
    }

    @RequestMapping(path = "/qna/{id}/edit", method = GET)
    public String editQuestionForm(HttpRequest request, HttpResponse response, Model model) {
        Long id = Long.valueOf(request.getQueryString().get("id"));
        model.addAttribute("question", questionService.findQuestion(id));
        return "qna/edit";
    }
}
