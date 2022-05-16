package com.forum.forum.Post;

import com.forum.forum.Category.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @SequenceGenerator(
            name = "posts_sequence",
            sequenceName = "posts_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "posts_sequence"
    )
    private Long id;
    private String text;
    private ArrayList<Category> categoriesList;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public List<Category> getCategoriesList() { return categoriesList; }

    public void setCategoriesList(ArrayList<Category> categoriesIdsList) { this.categoriesList = categoriesIdsList; }


    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", categoriesList=" + categoriesList +
                '}';
    }
}
