package com.forum.forum.Configuration.App;

import javax.persistence.*;

@Entity
@Table(name = "appRoles")
public class AppRole {
    @Id
    @SequenceGenerator(
            name = "appRoles_sequence",
            sequenceName = "appRoles_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "appRoles_sequence"
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