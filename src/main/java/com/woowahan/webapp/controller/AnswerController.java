package com.woowahan.webapp.controller;

import com.woowahan.webapp.model.Answer;
import com.woowahan.webapp.model.Question;
import com.woowahan.webapp.model.User;
import com.woowahan.webapp.repository.AnswerRepository;
import com.woowahan.webapp.repository.QuestionRepository;
import com.woowahan.webapp.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions/{questionId}/answers")
public class AnswerController {
    @Autowired
    private AnswerRepository answerRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("")
    public String create(@PathVariable Long questionId, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogOn(session)) {
            return "/users/login";
        }

        User writer = HttpSessionUtils.getUserFromSession(session);
        Question question = questionRepository.findOne(questionId);

        Answer answer = new Answer(question, writer, contents);
        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }

    @GetMapping("/{answerId}/form")
    public String editForm(@PathVariable Long questionId, @PathVariable Long answerId, HttpSession session, Model model) throws IllegalAccessException {
        if (!HttpSessionUtils.isLogOn(session)) {
            return "/users/login";
        }

        Answer answer = answerRepository.findOne(answerId);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if (!answer.isSameWriter(sessionedUser)) {
            throw new IllegalAccessException("You just can edit your own comment.");
        }

        model.addAttribute("answer", answer);
        return "answer/form";
    }

    @PutMapping("/{answerId}")
    public String edit(@PathVariable Long questionId, @PathVariable Long answerId, String contents, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isLogOn(session)) {
            return "redirect:/users/login";
        }

        Answer answer = answerRepository.findOne(answerId);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if (!answer.isSameWriter(sessionedUser)) {
            throw new IllegalAccessException("You just can edit your own comment.");
        }

        answer.update(contents);

        answerRepository.save(answer);

        return String.format("redirect:/questions/%d", questionId);
    }

    @DeleteMapping("/{answerId}")
    public String delete(@PathVariable long questionId, @PathVariable long answerId, HttpSession session) throws IllegalAccessException {
        if (!HttpSessionUtils.isLogOn(session)) {
            return "redirect:/users/login";
        }

        Answer answer = answerRepository.findOne(answerId);
        User sessionedUser = HttpSessionUtils.getUserFromSession(session);

        if (!answer.isSameWriter(sessionedUser)) {
            throw new IllegalAccessException("You just can delete your own comment.");
        }

        answerRepository.delete(answer);

        return String.format("redirect:/questions/%d", questionId);
    }
}
