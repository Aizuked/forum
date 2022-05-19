package com.forum.forum.Configuration.App.AppRole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Энтити класс пользователя сайта. Хранится в Postgres, таблица appUsers, поддерживается JPA & Hibernate.
 *
 */


@Entity
@Table(name = "appRoles")
public class AppRole implements Serializable {
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

    @Override
    public String toString() {
        return "AppRole{" +
                "id=" + id +
                ", roleName='" + roleName + '\'' +
                '}';
    }
}
