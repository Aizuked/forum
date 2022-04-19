package com.forum.forum.App;

import com.forum.forum.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository  extends JpaRepository<UserRole, Long> {
}
