package com.forum.forum.Post;

import com.forum.forum.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final CategoryService categoryService;

    public PostService(PostRepository postRepository, CategoryService categoryService) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
    }

    @Transactional
    public void addPost(String text, String cats) {
        ArrayList<String> catsNames = Arrays.stream(cats.split(", "))
                .collect(Collectors.toCollection(ArrayList::new));
        ArrayList<Long> categoriesIds = catsNames
                .stream()
                .map(categoryService::checkAddCategoryReturnId)
                .collect(Collectors.toCollection(ArrayList::new));

        Post post = new Post(text, categoriesIds);

        postRepository.save(post);
        postRepository.flush();
    }

    @Transactional
    public List<Post> getPostsByIdsList(List<Long> postIds) {
        return postIds
                .stream()
                .map(postRepository::getById)
                .toList();
    }

    @Transactional
    public List<Long> getAllValidNewsIds() {
        return postRepository
                .findAll()
                .stream()
                .map(Post::getId)
                .toList();
    }
}
