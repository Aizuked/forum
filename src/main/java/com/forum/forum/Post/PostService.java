package com.forum.forum.Post;

import com.forum.forum.Category.Category;
import com.forum.forum.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
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
        ArrayList<String> catsNames = new ArrayList<>();
        if (cats.split(", ").length > 0) {
            catsNames = Arrays.stream(cats.split(", "))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            catsNames.add(cats);
        }
        ArrayList<Category> categories = catsNames
                .stream()
                .map(categoryService::checkAddCategoryReturnId)
                .map(categoryService::getCategoryById)
                .collect(Collectors.toCollection(ArrayList::new));

        Post post = new Post();
        post.setText(text);
        post.setCategoriesList(categories);

        postRepository.save(post);
        postRepository.flush();
    }

    @Transactional
    public void updatePost(Post postToUpdate) {
        Post post = Optional.of(postRepository.getById(postToUpdate.getId()))
                .orElseThrow(() -> new IllegalStateException(
                        "\nFailed to update post with postId=" + postToUpdate.getId()
                ));
        if (cats.split(", ").length > 0) {
            catsNames = Arrays.stream(cats.split(", "))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            catsNames.add(cats);
        }
        post.setText(postToUpdate.getText());

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
