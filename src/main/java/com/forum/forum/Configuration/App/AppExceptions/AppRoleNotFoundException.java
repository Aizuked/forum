package com.forum.forum.Configuration.App.AppExceptions;

public class AppRoleNotFoundException extends RuntimeException {
    public AppRoleNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
