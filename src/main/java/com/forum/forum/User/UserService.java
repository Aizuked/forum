package com.forum.forum.User;

import com.forum.forum.Configuration.App.UserRole.UserRoleRepository;
import com.forum.forum.Configuration.App.UserRole.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

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
            user.setUserRoleIds(new ArrayList<Long>(List.of(1L)));

            userRepository.save(user);
            userRepository.flush();

            userRoleService.addUserToUserRoleRelation(user.getId(), 1L);
        } else {
            throw new IllegalStateException(
                    "Username:" + user.getUsername() + " already exists!"
            );
        }
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

        String encodedPassword = crypter.encode(user.getPassword());

        User user1 = userRepository.findUserByUsername(user.getUsername())
                .filter(userToCheck -> crypter.matches(user.getPassword(), userToCheck.getPassword()))
                .orElseThrow(() ->
                new UsernameNotFoundException(
                        "User:" + user.getUsername() + " was not found!"
                )
        );

        return !Objects.isNull(user1);
    }

}
