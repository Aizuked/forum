package com.forum.forum.Category;

import javax.persistence.*;

@Entity
@Table(name="categories")
public class Category {
    @Id
    @SequenceGenerator(
            name = "category_sequence",
            sequenceName = "category_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "category_sequence"
    )
    private Long id;
    private String categoryName;

    public Category() {
    }

    public Category(String categoryName) { this.categoryName = categoryName; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getCategoryName() { return categoryName; }

    public void setCategoryName(String categoryName) { this.categoryName = categoryName; }

}
