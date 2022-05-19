package com.forum.forum.Configuration.App.UserRole;

import com.forum.forum.Configuration.App.AppRole.AppRoleRepository;
import com.forum.forum.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;

/**
 * Сервис работы с энтити пользователя User.class, в основном транзакционного доступа к БД.
 * Зависит от сервиса ролей уровня пользовательского и имплементации Jpa репозитория.
 * Используется Spring Security -> UserDetailsServiceImpl
 *              контроллерами   -> AuthenticationController, IndexController, UsersController
 */


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
    public void addUserAppRoleRelation(Long user_id, ArrayList<Long> appRoleIds) {
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
