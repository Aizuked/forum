package com.forum.forum.Post;

import com.forum.forum.Category.Category;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Энтити класс постов пользователей. Хранится в PostgreSQL, таблица posts, поддерживается JPA & Hibernate.
 */


@Entity
@Table(name="posts")
public class Post implements Serializable {
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
    private Long id;                                //Id записи в базе данных.
    private String text;                            //Текст поста.
    private ArrayList<Category> categoriesList;     //Связный список категорий поста.
    private String postername;                      //Поле username энтити User, написвшего пост. Требуется на фронте.

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public String getText() { return text; }

    public void setText(String text) { this.text = text; }

    public List<Category> getCategoriesList() { return categoriesList; }

    public String getPosterName() { return postername; }

    public void setPosterName(String poster_name) { this.postername = poster_name; }

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
