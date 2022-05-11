package com.forum.forum.Configuration.App.UserRole;

import com.forum.forum.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class UserRoleService {
    @Autowired
    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) { this.userRoleRepository = userRoleRepository; }

    @Transactional
    public void addUserToUserRoleRelation(Long user_id, Long appRoleId) {
        UserRole userRole = new UserRole();
        userRole.setUser_id(user_id);
        userRole.setAppRoleId(appRoleId);

        userRoleRepository.save(userRole);
    }

    @Transactional
    public ArrayList<Long> getAppRoleIdsList(User user) {
        return user
                .getUserRoleIds()
                .stream()
                .map(userRoleRepository::getById)
                .map(UserRole::getAppRoleId)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
