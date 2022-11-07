package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;

public class Stat {
    @Expose
    private String name;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
