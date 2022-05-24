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

/**
 * Контроллер, отвечающий за регистрацию и авторизацию пользователей.
 * Указывается в Spring Security -> WebSecurityConfig для доступа всех пользователей на
 * /login и /registration. Также используется для Spring Security процесса авторизации
 * /processAuthentication. Еще реализует процесс выхода пользователя из аккаунта /process_logout.
 */


@Controller
public class AuthenticationController {

    @Autowired
    UserService userService;

    /**
     * Функция регистрации, создаёт новый объект User, добавляет его в модель, в
     * моделе он заполняется, а по кнопке отправляется обратно на /processRegistration.
     * Возвращает страницу шаблона registration.
     */
    @GetMapping("/registration")
    public String registrationPage(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute(user);
        return "registration";
    }

    /**
     * Функция обработки регистрации. Добавляет нового пользователя @user с предоставленными
     * пользователем на /registration параметрами. Пароль хэшируется методом pbkdf2.
     * Возвращает страницу шаблона login.
     */
    @PostMapping("/processRegistration")
    public String registrationProcessor(User user) {
        Pbkdf2PasswordEncoder crypter = new Pbkdf2PasswordEncoder("very_secret_secret");
        crypter.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

        String encodedPassword = crypter.encode(user.getPassword());
        user.setPassword(encodedPassword);

        userService.addUser(user);

        return "redirect:/login";
    }

    /**
     * Функция регистрации, создаёт новый объект User, добавляет его в модель, в
     * моделе он заполняется, а по кнопке отправляется обратно на /processAuthentication.
     * Возвращает страницу шаблона login.
     */
    @GetMapping("/login")
    public String loginPage(WebRequest request, Model model) {
        User user = new User();
        model.addAttribute(user);

        return "login";
    }

    /**
     * Функция обработки регистрации. Ищет пользователя @user с переданными на
     * /login параметрами.
     * В случае нахождения аутентифицирует пользователя и возвращает страницу шаблона home.
     * Иначе возврашает страницу ошибки аутентификации шаблона lohin.
     */
    @PostMapping("/processAuthentication")
    public String loginProcessor(User user) {
        if (userService.userFindAndMatch(user)) {
            return "home";
        } else {
            return "login?error=true";
        }
    }

    /**
     * Функция выхода пользователя из аккаунта.
     * Возвращает страницу шаблона login.
     */
    @PostMapping("/process_logout")
    public String logoutProcessor() {
        return "redirect:/login";
    }
}
