package com.forum.forum.Controllers;

import com.forum.forum.Category.CategoryService;
import com.forum.forum.Configuration.NewUserDetails;
import com.forum.forum.Post.Post;
import com.forum.forum.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;
import java.util.Map;

/**
 * Контроллер, отвечающий за ленту новостей. Постранично отображает все новости,
 * новости конкретного пользователя, новости конкретной категории. Так же отвечает
 * за добавление поста и обновления содержимого выбранного пользователем поста.
 */


@Controller
public class NewsController {
    @Autowired
    private final PostService postService;
    @Autowired
    private final CategoryService categoryService;

    public NewsController(PostService postService, CategoryService categoryService) {
        this.postService = postService;
        this.categoryService = categoryService;
    }

    /**
     * Функция отвечает за добавление нового поста по переданным Post параметрам.
     * Возвращает нулевую страницу общих новостей.
     */
    @PostMapping("/addNewPost")
    public String addPost(@RequestParam Map<String, String> paramMap, Principal principal) {
        postService.addPost(paramMap.get("text"), paramMap.get("categories"), principal.getName());
        return "redirect:/home/news";
    }

    /**
     * Функция отвечает за обновление выделенного поста по переданным Post параметрам.
     * Возвращает нулевую страницу общих новостей.
     */
    @PostMapping("/updatePost")
    public String updatePost(@RequestParam Map<String, String> paramMap) {
        postService.updatePost(paramMap.get("text"), paramMap.get("categories"), Long.valueOf(paramMap.get("postId")));
        return "redirect:/home/news";
    }

    @PostMapping("/deletePost")
    public String deletePost(@RequestParam Map<String, String> paramMap) {
        postService.deletePost(Long.valueOf(paramMap.get("postId")));
        return "redirect:/home/news";
    }

    /**
     * Функция формирует слайс списка новостей конкретного пользователя размера 10.
     * Возвращает страницу с этими новостями.
     */
    @GetMapping("/posts_of_user/{username}")
    public String userPosts(Model model, Authentication authentication,
                            @PathVariable String username,
                            @RequestParam(defaultValue = "0") Integer page) {
        List<Post> posts = postService                              //Слайс списка размером 10
                .getAllValidNewsOfUserSliced(page, username);

        NewUserDetails principal = (NewUserDetails)                 //Вытаскивает авторизированного на данный момент
                authentication.getPrincipal();                      //пользователя, для предоставления возможности
                                                                    //редактирования своих новостей.
        boolean adminFlag = principal.getAuthorities()              //В случае, если пользователь является администратором,
                .contains(new SimpleGrantedAuthority("ADMIN"));//пользователь получает возможность редактирования
                                                                    //всех новостей.
        model.addAttribute("adminFlag",
                adminFlag);
        model.addAttribute("principal",
                principal.getUsername());
        model.addAttribute("posts",
                posts);
        return "news";
    }

    @GetMapping("/posts_of_category/{category}")
    public String categoryPosts(Model model, Authentication authentication,
                                @PathVariable String category,
                                @RequestParam(defaultValue = "0") Integer page) {
        List<Post> posts = postService                              //Слайс списка размером 10
                .getAllValidNewsOfCategorySliced(page, category);

        NewUserDetails principal = (NewUserDetails)                 //Вытаскивает авторизированного на данный момент
                authentication.getPrincipal();                      //пользователя, для предоставления возможности
                                                                    //редактирования своих новостей.
        boolean adminFlag = principal.getAuthorities()              //В случае, если пользователь является администратором,
                .contains(new SimpleGrantedAuthority("ADMIN"));//пользователь получает возможность редактирования
                                                                    //всех новостей.
        model.addAttribute("adminFlag", adminFlag);
        model.addAttribute("principal", principal.getUsername());
        model.addAttribute("posts", posts);
        return "news";
    }

    @GetMapping("/home/news")
    public String newsPage(Model model, Authentication authentication,
                           @RequestParam(defaultValue = "0") Integer page) {
        List<Post> posts = postService                                  //Слайс списка размером 10
                .getAllValidNewsSliced(page);

        NewUserDetails principal = (NewUserDetails)                     //Вытаскивает авторизированного на данный момент
                authentication.getPrincipal();                          //пользователя, для предоставления возможности
                                                                        //редактирования своих новостей.
        boolean adminFlag = principal.getAuthorities()                  //В случае, если пользователь является администратором,
                .contains(new SimpleGrantedAuthority("ADMIN"));    //пользователь получает возможность редактирования
                                                                        //всех новостей.
        model.addAttribute("adminFlag", adminFlag);
        model.addAttribute("principal", principal.getUsername());
        model.addAttribute("posts", posts);
        return "news";
    }
}
