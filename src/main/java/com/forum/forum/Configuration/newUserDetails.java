package com.forum.forum.Configuration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.forum.forum.Configuration.App.AppRole;
import com.forum.forum.Configuration.App.AppRoleRepository;
import com.forum.forum.Configuration.App.UserRole;
import com.forum.forum.Configuration.App.UserRoleRepository;
import com.forum.forum.User.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class newUserDetails implements UserDetails {

    private User user;
    private UserRoleRepository userRoleRepository;

    public newUserDetails(User user, UserRoleRepository userRoleRepository) {
        this.user = user;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<UserRole> userRoles = Stream.of(user.getUserRoleId())
                .map(userRoleRepository::getById)
                .toList();

        return userRoles.stream()
                .map(UserRole::getUserAppRoleName)
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
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