package com.forum.forum.Category;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository; }

    @Transactional
    public String getCategoryNameById(Long id) {
        return categoryRepository
                .getById(id)
                .getCategoryName();
    }

    @Transactional
    public void addCategory(Category category) {
        categoryRepository.save(category);
        categoryRepository.flush();
    }

    @Transactional
    public Category getCategoryById(Long categoryId) {
        Category category = Optional.ofNullable(categoryRepository.getById(categoryId))
                .orElseThrow(() -> new IllegalStateException(
                        "\nCategory with id=" + categoryId + " was not found!"
                ));
        return category;
    }

    @Transactional
    public Long checkAddCategoryReturnId(String categoryName) {
        Category category = categoryRepository.findByCategoryName(categoryName);
        if (Objects.isNull(category)) {
            addCategory(new Category(categoryName));
        }
        category = categoryRepository.findByCategoryName(categoryName);
        return category.getId();
    }

    @Transactional
    public List<String> getRelatedCategoriesByPostIds(ArrayList<Long> postIds) {
        return postIds
                .stream()
                .map(categoryRepository::getById)
                .map(Category::getCategoryName)
                .toList();
    }
}
