package com.forum.forum.Configuration.App.AppRole;

import com.forum.forum.Configuration.App.UserRole.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Service
public class AppRoleService {
    @Autowired
    private final AppRoleRepository appRoleRepository;

    public AppRoleService(AppRoleRepository appRoleRepository) { this.appRoleRepository = appRoleRepository; }

    @Transactional
    public ArrayList<String> getAppRoleNamesByUserRoleIds (ArrayList<Long> userAppRoleIds) {
        return userAppRoleIds
                .stream()
                .map(appRoleRepository::getById)
                .map(AppRole::getRoleName)
                .collect(Collectors.toCollection(ArrayList::new));
    }
}
