package com.example.bottomnavigationproper.Models;

import java.io.Serializable;
import java.math.BigDecimal;

public class StatModel implements Serializable {
    private Long fixtureId;
    private Long playerId;
    private Boolean success;
    private Integer half;
    private String locationId;
    private String statNameId;
    private String timeOccurred;

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

    public String getLocationId() {
        return locationId;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public String getStatNameId() {
        return statNameId;
    }

    public void setStatNameId(String statNameId) {
        this.statNameId = statNameId;
    }

    public String getTimeOccurred() {
        return timeOccurred;
    }

    public void setTimeOccurred(String timeOccurred) {
        this.timeOccurred = timeOccurred;
    }
}
