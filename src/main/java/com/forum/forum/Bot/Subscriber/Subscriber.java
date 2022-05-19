package com.forum.forum.Bot.Subscriber;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Энтити класс пользователя сайта. Хранится в Postgres, таблица appUsers, поддерживается JPA & Hibernate.
 *
 */


@Entity
@Table(name = "news_subscribers")
public class Subscriber implements Serializable {
    @Id
    @SequenceGenerator(
            name = "subscribers",
            sequenceName = "subscribers",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "subscribers"
    )
    private Long id;
    private Integer vk_id;

    public Long getId() { return id; }

    public void setId(Long id) { this.id = id; }

    public Integer getVk_id() { return vk_id; }

    public void setVk_id(Integer vk_id) { this.vk_id = vk_id; }

    @Override
    public String toString() {
        return "Subscriber{" +
                "id=" + id +
                ", vk_id=" + vk_id +
                '}';
    }
}
