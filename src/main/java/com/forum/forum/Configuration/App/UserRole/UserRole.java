package com.forum.forum.Configuration.App.UserRole;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Энтити класс связи ролей пользователей и ролей уровня приложения.
 * Хранится в PostgreSQL, таблица userRoles, поддерживается JPA & Hibernate.
 *
 */


@Entity
@Table(name = "userRoles")
public class UserRole implements Serializable {

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
    private Long user_id;
    private Long appRoleId;

    public Long getAppRoleId() { return appRoleId; }

    public void setAppRoleId(Long appRoleId) { this.appRoleId = appRoleId; }

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Long getUser_id() { return user_id; }

    public void setUser_id(Long user_id) { this.user_id = user_id; }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", appRoleId=" + appRoleId +
                '}';
    }
}
