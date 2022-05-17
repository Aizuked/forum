package com.forum.forum.Configuration;

import com.forum.forum.Configuration.App.AppRole.AppRoleService;
import com.forum.forum.Configuration.App.UserRole.UserRoleService;
import com.forum.forum.User.User;
import com.forum.forum.User.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AppRoleService appRoleService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) {
        User user = userService.findUserByUsername(username);
        return new NewUserDetails(user, userRoleService, appRoleService);
    }

}