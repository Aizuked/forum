package com.forum.forum.Post;

import com.forum.forum.Bot.Bot;
import com.forum.forum.Category.Category;
import com.forum.forum.Category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class PostService {
    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final CategoryService categoryService;

    @Autowired
    private final PostPagingRepository postPagingRepository;

    @Autowired
    Bot bot;

    public PostService(PostRepository postRepository, CategoryService categoryService, PostPagingRepository postPagingRepository) {
        this.postRepository = postRepository;
        this.categoryService = categoryService;
        this.postPagingRepository = postPagingRepository;
    }

    @Transactional
    public void addPost(String text, String cats, String principal) {
        ArrayList<String> catsNames = new ArrayList<>();
        if (cats.split(" ").length > 0) {
            catsNames = Arrays.stream(cats.split(" "))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            catsNames.add(cats.trim());
        }
        ArrayList<Category> categories = catsNames
                .stream()
                .map(categoryService::checkAddCategoryReturnId)
                .map(categoryService::getCategoryById)
                .collect(Collectors.toCollection(ArrayList::new));

        Post post = new Post();
        post.setText(text);
        post.setCategoriesList(categories);
        post.setPosterName(principal);

        postRepository.save(post);
        postRepository.flush();

        bot.sendMsg("\"" + post.getText() + "\"" + " was posted by " + post.getPosterName());
    }

    @Transactional
    public void updatePost(String text, String cats, Long postId) {
        ArrayList<String> catsNames = new ArrayList<>();
        if (cats.split(" ").length > 0) {
            catsNames = Arrays.stream(cats.split(" "))
                    .collect(Collectors.toCollection(ArrayList::new));
        } else {
            catsNames.add(cats.trim());
        }
        ArrayList<Category> categories = catsNames
                .stream()
                .map(categoryService::checkAddCategoryReturnId)
                .map(categoryService::getCategoryById)
                .collect(Collectors.toCollection(ArrayList::new));

        Post post = Optional.of(postRepository.getById(postId))
                .orElseThrow(() -> new IllegalStateException(
                        "\nFailed to update post with postId=" + postId
                ));
        post.setText(text);
        post.setCategoriesList(categories);

        postRepository.save(post);
        postRepository.flush();
    }

    @Transactional
    public List<Post> getAllValidNewsOfUserSliced(Integer page, String username) {
        Pageable paging = PageRequest.of(page, 10);
        Slice<Post> slicedResults = postPagingRepository.findAll(paging);
        return slicedResults.getContent()
                .stream()
                .filter(post -> Objects.equals(post.getPosterName(), username))
                .toList();
    }

    @Transactional
    public List<Post> getAllValidNewsOfCategorySliced(Integer page, String categoryName) {
        Pageable paging = PageRequest.of(page, 10);
        Slice<Post> slicedResults = postPagingRepository.findAll(paging);
        return slicedResults.getContent()
                .stream()
                .filter(post -> {
                    for (Category cata: post.getCategoriesList()) {
                        if (cata.getCategoryName().equals(categoryName)) return true;
                    }
                    return false;
                })
                .toList();
    }

    @Transactional
    public List<Post> getAllValidNewsSliced(Integer page) {
        Pageable paging = PageRequest.of(page, 10);

        Slice<Post> slicedResults = postPagingRepository.findAll(paging);

        if (slicedResults.hasContent()) {
            return slicedResults.getContent();
        } else {
            return new ArrayList<Post>();
        }
    }
}
