package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class TeamsheetId implements Serializable {
    @Expose
    private Long fixtureId;
    @Expose
    private Long playerId;

    public TeamsheetId(){

    }

    public Long getFixtureId() {
        return fixtureId;
    }

    public void setFixtureId(Long fixtureId) {
        this.fixtureId = fixtureId;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }


}
