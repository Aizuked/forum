package com.forum.forum.Configuration;

import com.forum.forum.Configuration.App.AppExceptions.AppRoleNotFoundException;
import com.forum.forum.Configuration.App.UserRoleRepository;
import com.forum.forum.User.User;
import com.forum.forum.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException, AppRoleNotFoundException {

        User user = userRepository.getUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(
                        "User was not found!"
                )
            );

        return new newUserDetails(user, userRoleRepository);
    }

}