package com.woowahan.webapp.controller;

import com.woowahan.webapp.model.User;
import com.woowahan.webapp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        userRepository.save(user);
        return "redirect:/users";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable long id, Model model) {
        User user = userRepository.findOne(id);

        model.addAttribute("user", user);

        return "user/profile";
    }

    @GetMapping("")
    public String showAllUsers(Model model) {
        List<User> users = (List<User>) userRepository.findAll();
        model.addAttribute("users", users);

        return "user/list";
    }
}
