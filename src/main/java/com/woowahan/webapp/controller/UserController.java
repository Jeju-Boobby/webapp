package com.woowahan.webapp.controller;

import com.woowahan.webapp.model.User;
import com.woowahan.webapp.repository.UserRepository;
import com.woowahan.webapp.util.HttpSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserRepository userRepository;

    @PostMapping("")
    public String create(User user) {
        logger.debug("User : " + user);
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

    @GetMapping("/login")
    public String loginForm() {
        return "user/login";
    }

    @PostMapping("/login")
    public String login(String userId, String password, HttpSession session) {
        User user = userRepository.findByUserId(userId);

        if (user == null){
            return "redirect:/users/login";
        }

        if (!user.matchPassword(password)) {
            return "redirect:/users/login";
        }

        session.setAttribute(HttpSessionUtils.USER_SESSION_KEY, user);

        return "redirect:/";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(HttpSessionUtils.USER_SESSION_KEY);

        return "redirect:/";
    }

    @GetMapping("/register")
    public String registerForm() {
        return "user/form";
    }

    @GetMapping("/{id}/form")
    public String updateForm(@PathVariable long id, Model model, HttpSession session) throws IllegalAccessException {
        if (HttpSessionUtils.isLogOn(session)) {
            return "redirect:/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalAccessException("You just can update your own account.");
        }

        model.addAttribute("user", sessionedUser);

        return "user/form";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable long id, User updatedUser, HttpSession session) throws IllegalAccessException {
        if (HttpSessionUtils.isLogOn(session)) {
            return "redirect:/login";
        }

        User sessionedUser = HttpSessionUtils.getUserFromSession(session);
        if (!sessionedUser.matchId(id)) {
            throw new IllegalAccessException("You just can update your own account.");
        }

        logger.debug("Origin User: {}", sessionedUser);
        logger.debug("New User: {}", updatedUser);

        if (sessionedUser.match(updatedUser)) {
            sessionedUser.setName(updatedUser.getName());
            sessionedUser.setEmail(updatedUser.getEmail());
            userRepository.save(sessionedUser);
        }

        return "redirect:/users";
    }
}
