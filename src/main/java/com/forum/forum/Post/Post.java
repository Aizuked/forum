package com.forum.forum.Post;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="posts")
public class Post {
    @Id
    @SequenceGenerator(
            name = "appUser_sequence",
            sequenceName = "appUser_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appUser_sequence"
    )
    private Long id;
    private String text;
    private ArrayList<Long> categoriesIdsList;

    public Post() {
    }

    public Post(String text, ArrayList<Long> categoriesIdsList) {
        this.text = text;
        this.categoriesIdsList = categoriesIdsList;
    }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public List<Long> getCategoriesIdsList() { return categoriesIdsList; }

    public void setCategoriesIdsList(ArrayList<Long> categoriesIdsList) { this.categoriesIdsList = categoriesIdsList; }

    @Override
    public String toString() {
        return "Post{" +
                "id=" + id +
                ", text='" + text + '\'' +
                ", categoriesIdsList=" + categoriesIdsList +
                '}';
    }
}
