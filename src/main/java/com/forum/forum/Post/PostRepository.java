package com.forum.forum.Post;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa репозиторий доступа к PostgreSQL. Используются наследуемые у JpaRepository<T, ID> методы.
 */


public interface PostRepository extends JpaRepository<Post, Long> {
}
