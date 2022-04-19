package com.forum.forum.App;

import com.forum.forum.User.User;

import javax.persistence.*;

@Entity
@Table(name = "appRole")
public class AppRole {
    @Id
    @SequenceGenerator(
            name = "app_role_sequence",
            sequenceName = "app_role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "app_role_sequence"
    )
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private User user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id")
    private UserRole userRole;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }

    public void setUser(User user) { this.user = user; }

    public UserRole getUserRole() { return userRole; }

    public void setUserRole(UserRole userRole) { this.userRole = userRole; }

}
