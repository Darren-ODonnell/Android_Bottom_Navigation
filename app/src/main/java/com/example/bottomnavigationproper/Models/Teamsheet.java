package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

public class Teamsheet implements Serializable {
    @Expose
    private TeamsheetId id;
    @Expose
    private Fixture fixture;
    @Expose
    private Player player;
    @Expose
    private Position position;

    private int jerseyNumber;

    public Teamsheet(){

    }

    public TeamsheetId getId() {
        return id;
    }

    public void setId(TeamsheetId id) {
        this.id = id;
    }

    public Fixture getFixture() {
        return fixture;
    }

    public void setFixture(Fixture fixture) {
        this.fixture = fixture;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getJerseyNumber() {
        return jerseyNumber;
    }

    public void setJerseyNumber(int jerseyNumber) {
        this.jerseyNumber = jerseyNumber;
    }

    @Override
    public String toString(){
        return player.toString();
    }
}

