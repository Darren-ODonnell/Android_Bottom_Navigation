package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PlayerResponse {

    @SerializedName("items")
    @Expose
    List<Player> items = null;

    public List<Player> getItems() {
        return items;
    }

    public void setItems(List<Player> items) {
        this.items = items;
    }
}
