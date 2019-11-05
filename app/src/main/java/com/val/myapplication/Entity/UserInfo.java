package com.val.myapplication.Entity;

public class UserInfo {
    private String password;
    private String login;

    public UserInfo(String password, String login) {
        this.password = password;
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public String getLogin() {
        return login;
    }
}
