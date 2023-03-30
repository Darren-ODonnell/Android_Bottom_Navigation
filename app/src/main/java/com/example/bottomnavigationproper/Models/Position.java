package com.example.bottomnavigationproper.Models;


import com.google.gson.annotations.Expose;

public class Position {

    @Expose
    private Long id;

    @Expose
    private String name;

    @Expose
    private String abbrev;

    public Position(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbbrev() {
        return abbrev;
    }

    public void setAbbrev(String abbrev) {
        this.abbrev = abbrev;
    }

}