package com.forum.forum.Controllers;

import com.forum.forum.User.User;
import com.forum.forum.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;


@Controller
public class UsersController {
@Autowired
    private final UserService userService;

    public UsersController(UserService userService) { this.userService = userService; }

    @GetMapping("/home/loadAllUsers")
    public String loadAllUsers(Model model) {
        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "loadAllUsers";
    }
}
