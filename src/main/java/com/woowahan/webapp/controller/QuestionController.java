package com.woowahan.webapp.controller;

import com.woowahan.webapp.model.Question;
import com.woowahan.webapp.repository.QuestionRepository;
import com.woowahan.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/questions")
public class QuestionController {
    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(String writer, String title, String contents) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();
        Question question = new Question(userRepository.findByUserId(writer), title, contents, dateFormat.format(date));
        questionRepository.save(question);

        return "redirect:/";
    }

    @GetMapping("")
    public String showList() {
        return "redirect:/";
    }

    @GetMapping("/form")
    public String form() {
        return "qna/form";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        Question question = questionRepository.findOne(id);
        model.addAttribute("question", question);

        return "qna/show";
    }

    @GetMapping("/{id}/form")
    public String editForm(@PathVariable long id, Model model) {
        Question question = questionRepository.findOne(id);
        model.addAttribute("question", question);

        return "qna/form";

    }

    @PutMapping("/{id}")
    public String edit(@PathVariable long id, String title, String contents) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date();

        Question question = questionRepository.findOne(id);

        question.setTitle(title);
        question.setContents(contents);
        question.setTime(dateFormat.format(date));

        questionRepository.save(question);

        return "redirect:/";
    }
}
