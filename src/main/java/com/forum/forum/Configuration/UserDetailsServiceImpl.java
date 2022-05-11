package com.forum.forum.Configuration;

import com.forum.forum.Configuration.App.AppRole.AppRoleService;
import com.forum.forum.Configuration.App.UserRole.UserRoleService;
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
    private UserRoleService userRoleService;

    @Autowired
    private AppRoleService appRoleService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username).orElseThrow(() ->
                new UsernameNotFoundException(
                        "User was not found!"
                )
            );

        return new newUserDetails(user, userRoleService, appRoleService);
    }

}