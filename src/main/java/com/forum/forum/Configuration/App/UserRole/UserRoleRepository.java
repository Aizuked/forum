package com.forum.forum.Configuration.App.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Jpa репозиторий доступа к PostgreSQL. Используются как наследуемые у JpaRepository<T, ID> методы,
 * так и реализуется Optional метод для нахождения пользователя по строке поля username.
 */


public interface UserRoleRepository  extends JpaRepository<UserRole, Long> {
}
