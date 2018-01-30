package com.woowahan.webapp.controller;

import com.woowahan.webapp.model.Question;
import com.woowahan.webapp.model.Result;
import com.woowahan.webapp.model.User;
import com.woowahan.webapp.service.QuestionService;
import com.woowahan.webapp.util.HttpSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionService questionService;

    @GetMapping("")
    public String showList() {
        return "redirect:/";
    }

    @PostMapping("")
    public String create(String title, String contents, HttpSession session) {
        if (!HttpSessionUtils.isLogOn(session)) {
            return "users/login";
        }

        User writer = HttpSessionUtils.getUserFromSession(session);
        questionService.save(writer, title, contents);

        return "redirect:/";
    }

    @GetMapping("/form")
    public String form(HttpSession session) {
        if (!HttpSessionUtils.isLogOn(session)) {
            return "users/login";
        }
        return "qna/form";
    }

    @GetMapping("/{id}/form")
    public String editForm(@PathVariable long id, Model model, HttpSession session) {
        Question question = questionService.findOne(id);
        Result result = valid(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        model.addAttribute("question", question);
        return "qna/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        Question question = questionService.findOne(id);
        model.addAttribute("question", question);

        return "qna/show";
    }

    @PutMapping("/{id}")
    public String edit(@PathVariable long id, String title, String contents, Model model, HttpSession session) {
        Question question = questionService.findOne(id);
        Result result = valid(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }

        questionService.update(question, title, contents);
        return String.format("redirect:/questions/%d", id);
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id, Model model, HttpSession session) {
        Question question = questionService.findOne(id);
        Result result = valid(session, question);

        if (!result.isValid()) {
            model.addAttribute("errorMessage", result.getErrorMessage());
            return "/user/login";
        }
        
        questionService.delete(question);
        return "redirect:/";
    }

    private Result valid(HttpSession session, Question question) {
        if (!HttpSessionUtils.isLogOn(session)) {
            return Result.fail("로그인이 필요합니다.");
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (question.isSameWriter(sessionedUser)) {
            return Result.fail("자신의 글만 수정 및 삭제할 수 있습니다.");
        }

        return Result.ok();
    }
}
