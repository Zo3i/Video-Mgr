package com.jo.bean;

public class UserAdmin {
    private String userName;
    private String password;
    private String token;

    public UserAdmin(String userName, String password, String token) {
        this.userName = userName;
        this.password = password;
        this.token = token;
    }

    public UserAdmin() {
        super();
    }

    public UserAdmin(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
