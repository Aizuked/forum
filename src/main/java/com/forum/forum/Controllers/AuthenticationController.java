package com.forum.forum.Controllers;

import com.forum.forum.User.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class AuthenticationController {

    @RequestMapping("/registration")
    public String showRegistrationForm(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "registration";
    }

    @RequestMapping("/login")
    public String showLoginForm(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }
}
