package com.woowahan.webapp.controller;

import com.woowahan.webapp.model.Answer;
import com.woowahan.webapp.model.Question;
import com.woowahan.webapp.model.Result;
import com.woowahan.webapp.model.User;
import com.woowahan.webapp.repository.AnswerRepository;
import com.woowahan.webapp.repository.QuestionRepository;
import com.woowahan.webapp.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/questions/{questionId}/answers")
public class ApiAnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public Answer create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogOn(session)) {
            return null;
        }

        User writer = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);

        Answer answer = new Answer(question, writer, contents);
        return answerRepository.save(answer);
    }

    @GetMapping("/{answerId}/form")
    public String editForm(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session, Model model) {
        Answer answer = answerRepository.findOne(answerId);
        Result result = valid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        model.addAttribute("answer", answer);
        return "answer/form";
    }

    @PutMapping("/{answerId}")
    public String edit(@PathVariable Long questionId, @PathVariable Long answerId, String contents, Model model, HttpSession session) {
        Answer answer = answerRepository.findOne(answerId);
        Result result = valid(session, answer);
        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        answer.update(contents);

        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{answerId}")
    public Result delete(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) {
        Answer answer = answerRepository.findOne(answerId);
        Result result = valid(session, answer);
        if (!result.isValid()) {
            return result;
        }

        answerRepository.delete(answer);
        return result;
    }

    private Result valid(HttpSession session, Answer answer) {
        if (!HttpSessionUtils.isLogOn(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!answer.isSameWriter(sessionedUser)) {
            return Result.fail("자신의 댓글만 수정 및 삭제할 수 있습니다.");
        }

        return Result.ok();
    }
}
