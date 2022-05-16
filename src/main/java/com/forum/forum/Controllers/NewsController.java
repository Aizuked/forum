package com.forum.forum.Controllers;

import com.forum.forum.Category.Category;
import com.forum.forum.Category.CategoryService;
import com.forum.forum.Post.Post;
import com.forum.forum.Post.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @PostMapping("/addNewPost")
    public String addPost(@RequestParam Map<String, String> paramMap) {
        postService.addPost(paramMap.get("text"), paramMap.get("categories"));
        return "redirect:/home/news";
    }

    @PostMapping("/updatePost")
    public String updatePost(@RequestParam Map<String, String> paramMap) {
        Post post = new Post();
        post.setText(paramMap.get("text"));
        post.setCategoriesList(paramMap.get("categories"));
        postService.updatePost();
        return "redirect:/home/news";
    }

    @GetMapping("/home/news")
    public String newsPage(Model model) {
        ArrayList<Long> ids = new ArrayList<>();
        Post postToUpdate = new Post();

        List<Post> posts = postService.getPostsByIdsList(postService.getAllValidNewsIds());

        model.addAttribute("posts", posts);
        model.addAttribute(postToUpdate);
        return "news";
    }
}
