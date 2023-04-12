package com.example.bottomnavigationproper;

import com.example.bottomnavigationproper.Models.Fellowship;

import java.util.List;

public class User {
    private int id;
    private String username;
    private String email;
    private String accessToken;
    private final String tokenType = "Bearer";
    private List<String> roles;
    private Fellowship fellow;


    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Fellowship getFellow() {
        return fellow;
    }

    public void setFellow(Fellowship fellow) {
        this.fellow = fellow;
    }
}

