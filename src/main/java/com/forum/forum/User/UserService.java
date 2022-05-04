package com.forum.forum.User;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public void addUser(User user) {
        Optional<User> userToCheck = userRepository.findUserByUsername(user.getUsername());
        if (userToCheck.isEmpty()) {
            userRepository.save(user);
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
    public boolean userFindAndMatch(User user1) {
        User user = userRepository.findUserByUsername(user1.getUsername()).orElseThrow(() ->
                new UsernameNotFoundException(
                        "User:" + user1.getUsername() + " was not found!"
                )
        );

        return Objects.equals(user.getPassword(), user1.getPassword());
    }

}
