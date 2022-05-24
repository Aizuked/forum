package com.forum.forum.User;

import com.forum.forum.Configuration.App.UserRole.UserRoleService;
import com.forum.forum.Post.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Сервис работы с энтити пользователя User.class, транзакционного доступа к БД.
 * Зависит от сервиса ролей уровня пользовательского и имплементации Jpa репозитория.
 * Используется Spring Security -> UserDetailsServiceImpl
 *              контроллерами   -> AuthenticationController, IndexController, UsersController.
 */


@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserRoleService userRoleService;

    public UserService(UserRepository userRepository, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }

    /**
     * Функция добавления новых пользователей.
     * @userToCheck хранит пользователя, найденного в репозитории по полю username, в случае его существования.
     * Иначе добавляет полученого функцией пользователя в БД.
     */
    @Transactional
    public void addUser(User user) {
        Optional<User> userToCheck = userRepository.findUserByUsername(user.getUsername());

        if (userToCheck.isEmpty()) {
            if (Objects.equals(user.getUsername(), "zxc")) {     //Для отладки - помимо пользователей, написавших
                                                                    //пост, их посты могут редактировать только
                                                                    //администраторы.
                user.setUserRoleIds                                 //Наделяет данного пользователя авторитетами
                        (new ArrayList<Long>(List.of(1L, 2L)));     //пользователя и администратора.
                userRoleService.addUserAppRoleRelation              //Создаёт записи сочетаний id пользователя
                        (user.getId(), new ArrayList<Long>(List.of(1L, 2L)));   //и id его ролей уровня приложения.
            }
            else {                                                  //Аналогично
                user.setUserRoleIds
                        (new ArrayList<Long>(List.of(1L)));
                userRoleService.addUserAppRoleRelation
                        (user.getId(), new ArrayList<Long>(List.of(1L)));
            }

            userRepository.save(user);                              //Сохранение в БД.
            userRepository.flush();                                 //Отправляет накопленные изменения в БД.


        } else {
            throw new IllegalStateException(
                    "\nUsername:" + user.getUsername() + " already exists!"
            );
        }
    }

    /**
     * Возвращает пользователя user с полем username равным полученному функцией аргументу @username.
     * Иначе кидает ошибку отсутствия такого пользователя.
     */
    @Transactional
    public User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(
                        "\nUser with username=" + username + " was not found!"
                ));
        return user;
    }

    /**
     * Удаляет пользователя по аргументу имени пользователя @username.
     * Иначе кидает ошибку отсутствия такого пользователя.
     */
    @Transactional
    public void deleteUser(String username) {
        userRepository.findUserByUsername(username).orElseThrow(() ->
                new IllegalStateException(
                        "\nUser with username=" + username + " was not found!"
                )
        );
    }

    /**
     * Ищет пользователя по полю username аргумента @user, затем применяет фильтр
     * соответствия зашифрованного пароля password у найденного (в случае существования такового)
     * с полученным у аргумента @user полем password, аналогично зашифрованным методом pbkdf2.
     * Важно отметить сравнение не по хэшу строк, а по методу
     * Pbkdf2PasswordEncoder.matches(Charsequence, String), поскольку pbkdf2 каждый раз генерирует разный хэш.
     * Возвращает true, в случае совпадения (по username и password) предоставленного аргумента @user с записью в БД.
     */
    @Transactional
    public boolean userFindAndMatch(User user) {
        Pbkdf2PasswordEncoder crypter = new Pbkdf2PasswordEncoder("very_secret_secret");
        crypter.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

        User user1 = userRepository.findUserByUsername(user.getUsername())
                .filter(userToCheck -> crypter.matches(user.getPassword(), userToCheck.getPassword()))
                .orElseThrow(() ->
                new UsernameNotFoundException(
                        "\nUser:" + user.getUsername() + " was not found!"
                )
        );

        return !Objects.isNull(user1);
    }

    @Transactional
    public void addPostToUserPosts(Post post, String username) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException(
                        "\nFailed to find User with username=" + username
                ));

        if (user.getPosts() != null) {
            user.getPosts().add(post);
        } else {
            user.setPosts(new ArrayList<>(List.of(post)));
        }

        userRepository.save(user);
        userRepository.flush();
    }

    @Transactional
    public void deletePostFromUserByUserName(String username, Post post) {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new IllegalStateException(
                        "\nFailed to find User with username=" + username
                ));

        user.getPosts().removeIf(post1 -> post1 == post);

        userRepository.save(user);
    }

}
