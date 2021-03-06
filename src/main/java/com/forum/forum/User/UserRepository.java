package com.forum.forum.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Jpa репозиторий доступа к PostgreSQL. Используются как наследуемые у JpaRepository<T, ID> методы,
 * так и реализуется Optional метод для нахождения пользователя по строке поля username.
 */


public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);
}
