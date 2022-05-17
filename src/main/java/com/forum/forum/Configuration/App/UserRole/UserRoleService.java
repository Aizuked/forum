package com.forum.forum.Configuration.App.UserRole;

import com.forum.forum.Configuration.App.AppRole.AppRoleRepository;
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

    @Autowired
    private final AppRoleRepository appRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository, AppRoleRepository appRoleRepository) {
        this.userRoleRepository = userRoleRepository;
        this.appRoleRepository = appRoleRepository;
    }

    @Transactional
    public void addUserToUserRoleRelation(Long user_id, ArrayList<Long> appRoleIds) {
        UserRole userRole = new UserRole();
        userRole.setUser_id(user_id);
        for (Long approleId : appRoleIds) {
            userRole.setAppRoleId(approleId);
            userRoleRepository.save(userRole);
        }

    }

    @Transactional
    public ArrayList<Long> getUserRoleIdsList(User user) {
        return new ArrayList<>(user
                .getUserRoleIds());
    }
}
