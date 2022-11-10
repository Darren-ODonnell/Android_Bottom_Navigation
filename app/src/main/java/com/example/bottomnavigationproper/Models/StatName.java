package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class StatName implements Serializable {
    @Expose
    private String id;
    @Expose
    private String name;

    public StatName() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
