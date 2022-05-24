package com.forum.forum.Controllers;

import com.forum.forum.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


/**
 * Контроллер, отвечающий за индексную страницу.
 * Не несёт практического функционала, тестовый, на удаление.
 */


@Controller
public class IndexController {

    @Autowired
    private final UserService userService;

    public IndexController(UserService userService) {
        this.userService = userService;
    }


    @RequestMapping(value = {"/", "/home"})
    public String showGreetings(Model model, Principal principal) {
        model.addAttribute("principal", principal.getName());
        return "home";
    }
}
