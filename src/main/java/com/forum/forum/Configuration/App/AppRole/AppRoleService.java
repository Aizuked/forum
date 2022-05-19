package com.forum.forum.Configuration.App.AppRole;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Сервис работы с энтити пользователя User.class, в основном транзакционного доступа к БД.
 * Зависит от сервиса ролей уровня пользовательского и имплементации Jpa репозитория.
 * Используется Spring Security -> UserDetailsServiceImpl
 *              контроллерами   -> AuthenticationController, IndexController, UsersController
 */


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
