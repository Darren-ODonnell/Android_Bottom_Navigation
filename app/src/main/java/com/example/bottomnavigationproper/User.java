package com.example.bottomnavigationproper;

import com.example.bottomnavigationproper.Models.Fellowship;

import java.util.List;

public class User {
    private int id;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType(){
        return this.tokenType;
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

