package com.forum.forum.Controllers;

import com.forum.forum.User.User;
import com.forum.forum.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;

@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;

    @GetMapping("/registration")
    public String registrationPage(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute(user);
        return "registration";
    }

    @PostMapping("/processRegistration")
    public String registrationProcessor(User user) {
        Pbkdf2PasswordEncoder crypter = new Pbkdf2PasswordEncoder("very_secret_secret");
        crypter.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

        String encodedPassword = crypter.encode(user.getPassword());
        user.setPassword(encodedPassword);

        user.setUserRoleId(new Long[]{1L});

        userService.addUser(user);

        return "login";
    }
    @GetMapping("/login")
    public String loginPage(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute(user);

        return "login";
    }

    @PostMapping("/processAuthentication")
    public String loginProcessor(User user) {
        Pbkdf2PasswordEncoder crypter = new Pbkdf2PasswordEncoder("very_secret_secret");
        crypter.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

        String encodedPassword = crypter.encode(user.getPassword());
        user.setPassword(encodedPassword);

        if (userService.userFindAndMatch(user)) {
            return "home";
        } else {
            return "redirect:login?error=true";
        }
    }

    @GetMapping("/logout")
    public String logout(WebRequest request, Model model) {
        return "login";
    }

}
