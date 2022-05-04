package com.forum.forum.Configuration.App;

import com.forum.forum.User.User;

import javax.persistence.*;

@Entity
@Table(name = "userRoles")
public class UserRole {

    @Id
    @SequenceGenerator(
            name = "userRoles_sequence",
            sequenceName = "userRoles_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "userRoles_sequence"
    )
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String userAppRoleName;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public String getUserAppRoleName() {
        return userAppRoleName;
    }

    public void setUserAppRoleName(String userAppRoleName) {
        this.userAppRoleName = userAppRoleName;
    }
}
