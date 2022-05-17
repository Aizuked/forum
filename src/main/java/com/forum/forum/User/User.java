package com.forum.forum.User;

import com.forum.forum.Post.Post;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;


@Entity
@Table(name = "appUsers")
public class User implements Serializable {
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
    private String username;
    private String password;
    private ArrayList<Post> posts;
    private ArrayList<Long> userRoleIds;
    private boolean enabled = true;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() { return username; }

    public void setUsername(String login) {
        this.username = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Post> getPosts() { return posts; }

    public void setPosts(ArrayList<Post> posts) { this.posts = posts; }

    public ArrayList<Long> getUserRoleIds() { return userRoleIds; }

    public void setUserRoleIds(ArrayList<Long> userRoleId) {
        this.userRoleIds = userRoleId;
    }

    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", posts=" + posts +
                ", userRoleIds=" + userRoleIds +
                ", enabled=" + enabled +
                '}';
    }
}
