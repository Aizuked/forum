package com.forum.forum.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.forum.forum.Configuration.App.AppRole.AppRoleService;
import com.forum.forum.Configuration.App.UserRole.UserRole;
import com.forum.forum.Configuration.App.UserRole.UserRoleService;
import com.forum.forum.User.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.transaction.Transactional;

public class newUserDetails implements UserDetails {

    private User user;
    private UserRoleService userRoleService;
    private AppRoleService appRoleService;

    public newUserDetails(User user, UserRoleService userRoleService, AppRoleService appRoleService) {
        this.user = user;
        this.userRoleService = userRoleService;
        this.appRoleService = appRoleService;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<Long> userAppRoleIds = userRoleService.getAppRoleIdsList(user);

        ArrayList<String> userAppRoleNames = appRoleService.getAppRoleNamesByUserRoleIds(userAppRoleIds);

        return userAppRoleNames
                .stream()
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return String.valueOf("{pbkdf2}" + user.getPassword());
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}