package com.forum.forum.App;

import javax.persistence.*;

@Entity
@Table(name = "userRole")
public class UserRole {
    @Id
    @SequenceGenerator(
            name = "user_role_sequence",
            sequenceName = "user_role_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_sequence"
    )
    private Long id;
    private String roleName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

}
