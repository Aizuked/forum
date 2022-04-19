package com.forum.forum.Controllers;

import com.forum.forum.User.User;
import com.forum.forum.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class IndexController {

    @Autowired
    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = {"/", "/home"})
    public String showLoginForm(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "home";
    }
}
