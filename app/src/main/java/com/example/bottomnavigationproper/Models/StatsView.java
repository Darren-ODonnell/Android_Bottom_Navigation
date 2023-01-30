package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;

public class StatsView implements Serializable {
    @Expose
    private String statName;
    @Expose
    private Boolean success;
    @Expose
    private Integer half;
    @Expose
    private Integer season;
    @Expose
    private String firstName;
    @Expose
    private String lastName;
    @Expose
    private String homeTeam;
    @Expose
    private String awayTeam;
    @Expose
    private String location;
    @Expose
    private String fixtureDate;
    @Expose
    private BigDecimal timeOccurred;
    @Expose
    private String count;

    public StatsView(){

    }

    public String getStatName() {
        return statName;
    }

    public void setStatName(String statName) {
        this.statName = statName;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getHalf() {
        return half;
    }

    public void setHalf(Integer half) {
        this.half = half;
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomeTeam() {
        return homeTeam;
    }

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public String getAwayTeam() {
        return awayTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public String getFixtureDate() {
        return fixtureDate;
    }

    public void setFixtureDate(String fixtureDate) {
        this.fixtureDate = fixtureDate;
    }

    public BigDecimal getTimeOccurred() {
        return timeOccurred;
    }

    public void setTimeOccurred(BigDecimal timeOccurred) {
        this.timeOccurred = timeOccurred;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Stat{" +
                "statName='" + statName + '\'' +
                ", success=" + success +
                ", half=" + half +
                ", season=" + season +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", homeTeam='" + homeTeam + '\'' +
                ", awayTeam='" + awayTeam + '\'' +
                ", location='" + location + '\'' +
                ", fixtureDate='" + fixtureDate + '\'' +
                ", timeOccurred='" + timeOccurred + '\'' +
                ", count='" + count + '\'' +
                '}';
    }
}
