package com.woowahan.webapp.controller;

import com.woowahan.webapp.model.Question;
import com.woowahan.webapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class IndexController {
    @Autowired
    private QuestionRepository questionRepository;

    @GetMapping("")
    public String home(Model model) {
        List<Question> questions = (List<Question>) questionRepository.findAll();

        model.addAttribute("questions", questions);

        return "index";
    }

    @GetMapping("/index")
    public String index() {
        return "redirect:/";
    }
}
