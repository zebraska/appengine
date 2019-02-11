package com.zenika.zencontact.ressource.auth;

import com.google.appengine.api.users.*;

public class AuthenticationService{

    private static AuthenticationService INSTANCE =
        new AuthenticationService();

    public static AuthenticationService getInstance(){
        return INSTANCE;
    }

    private UserService userService =
        UserServiceFactory.getUserService();

    public String getLoginURL(String url){
        return userService.createLoginURL(url);
    }

    public String getLogoutURL(String url){
        return userService.createLogoutURL(url);
    }

    public boolean isAdmin(){
        return userService.isUserAdmin();
    }

    public User getUser(){
        return userService.getCurrentUser();
    }

    public String getUsername(){
        return userService.getUser().getNickname();
    }
}