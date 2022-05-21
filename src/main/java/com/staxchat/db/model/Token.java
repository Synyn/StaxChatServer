package com.staxchat.db.model;

import javax.management.relation.Role;

public class Token {

    public Token(){}

    private String token;
    private Role role;

    public Token(String token, Role role) {
        this.token = token;
        this.role = role;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
