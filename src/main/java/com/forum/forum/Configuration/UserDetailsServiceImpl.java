package com.forum.forum.Configuration;

import com.forum.forum.App.AppRoleRepository;
import com.forum.forum.User.User;
import com.forum.forum.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AppRoleRepository appRoleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException("user was not found!")
            );

        List<String> roleNames = appRoleRepository.getAllRoles().stream().map();
        return new newUserDetails(user);
    }

}