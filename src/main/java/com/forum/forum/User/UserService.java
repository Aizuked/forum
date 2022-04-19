package com.forum.forum.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user) {
        userRepository.findUserByUsername(user.getUsername()).orElseThrow(() ->
                new IllegalStateException(
                        "Username:" + user.getUsername() + " already exists!"
                )
        );
        userRepository.save(user);
    }

    public void deleteUser(User user) {
        userRepository.findById(user.getId()).orElseThrow(() ->
                new IllegalStateException(
                        "User:" + user.toString() + " was not found!"
                )
        );
    }

}
