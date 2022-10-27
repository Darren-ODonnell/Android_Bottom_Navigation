package com.example.bottomnavigationproper.Models;

import java.sql.Date;
import java.sql.Time;

public class Fixture {
    private Long competitionId;
    private Long homeTeamId;
    private Long awayTeamId;
    private Date fixtureDate;
    private Time fixtureTime;
    private Integer season;
    private Integer round;

    public Long getCompetitionId() {
        return competitionId;
    }

    public void setCompetitionId(Long competitionId) {
        this.competitionId = competitionId;
    }

    public Long getHomeTeamId() {
        return homeTeamId;
    }

    public void setHomeTeamId(Long homeTeamId) {
        this.homeTeamId = homeTeamId;
    }

    public Long getAwayTeamId() {
        return awayTeamId;
    }

    public void setAwayTeamId(Long awayTeamId) {
        this.awayTeamId = awayTeamId;
    }

    public Date getFixtureDate() {
        return fixtureDate;
    }

    public void setFixtureDate(Date fixtureDate) {
        this.fixtureDate = fixtureDate;
    }

    public Time getFixtureTime() {
        return fixtureTime;
    }

    public void setFixtureTime(Time fixtureTime) {
        this.fixtureTime = fixtureTime;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    @Override
    public String toString() {
        return this.fixtureDate.toString();
    }
}
