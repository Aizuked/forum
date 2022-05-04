package com.forum.forum.User;
import com.forum.forum.Configuration.App.UserRole;

import javax.persistence.*;


@Entity
@Table(name = "appUsers")
public class User {
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
    @Lob
    private String related_news;
    private Long[] userRoleId;

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

    public String getRelated_news() {
        return related_news;
    }

    public void setRelated_news(String related_news) {
        this.related_news = related_news;
    }

    public Long[] getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Long[] userRoleId) {
        this.userRoleId = userRoleId;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", related_news='" + related_news + '\'' +
                ", userRoleId=" + userRoleId +
                '}';
    }
}
