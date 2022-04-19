package com.forum.forum.App;

import com.forum.forum.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AppRoleRepository  extends JpaRepository<AppRole, Long> {
    List<Optional<AppRole>> getAllRoles();
}
