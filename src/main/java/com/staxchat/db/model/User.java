package com.staxchat.db.model;

import com.staxchat.db.BaseEntity;
import org.apache.log4j.Logger;


public class User extends BaseEntity {
    private String username;
    private String password;
    private String token;

    private static Logger logger = Logger.getLogger(User.class);

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
