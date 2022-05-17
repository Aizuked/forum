package com.forum.forum.User;

import com.forum.forum.Configuration.App.UserRole.UserRoleRepository;
import com.forum.forum.Configuration.App.UserRole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final UserRoleService userRoleService;

    public UserService(UserRepository userRepository, UserRoleRepository userRoleRepository, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.userRoleService = userRoleService;
    }

    @Transactional
    public void addUser(User user) {
        Optional<User> userToCheck = userRepository.findUserByUsername(user.getUsername());

        if (userToCheck.isEmpty()) {
            if (Objects.equals(user.getUsername(), "zxc")) {
                user.setUserRoleIds(new ArrayList<Long>(List.of(1L, 2L)));
                userRoleService.addUserToUserRoleRelation(user.getId(), new ArrayList<Long>(List.of(1L, 2L)));
            }
            else {
                user.setUserRoleIds(new ArrayList<Long>(List.of(1L)));
                userRoleService.addUserToUserRoleRelation(user.getId(), new ArrayList<Long>(List.of(1L)));
            }

            userRepository.save(user);
            userRepository.flush();


        } else {
            throw new IllegalStateException(
                    "Username:" + user.getUsername() + " already exists!"
            );
        }
    }

    @Transactional
    public User findUserByUsername(String username) {
        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(
                        "User with username=" + username + " was not found"
                ));
        return user;
    }

    @Transactional
    public void deleteUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(() ->
                new IllegalStateException(
                        "User:" + user.getUsername() + " was not found!"
                )
        );
    }

    @Transactional
    public boolean userFindAndMatch(User user) {
        Pbkdf2PasswordEncoder crypter = new Pbkdf2PasswordEncoder("very_secret_secret");
        crypter.setAlgorithm(Pbkdf2PasswordEncoder.SecretKeyFactoryAlgorithm.PBKDF2WithHmacSHA512);

        User user1 = userRepository.findUserByUsername(user.getUsername())
                .filter(userToCheck -> crypter.matches(user.getPassword(), userToCheck.getPassword()))
                .orElseThrow(() ->
                new UsernameNotFoundException(
                        "User:" + user.getUsername() + " was not found!"
                )
        );

        return !Objects.isNull(user1);
    }

    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

}
