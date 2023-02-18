package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;

public class Result {
    @Expose
    String homeTeam ;
    @Expose
    String awayTeam ;
    // individual breakdown of home/away goals/points
    @Expose
    long homePoints ;
    @Expose
    long homeGoals ;
    @Expose
    long awayPoints ;
    @Expose
    long awayGoals ;

    // all scores converted to points - so winner can be easily determined
    @Expose
    long awayScorePoints ;
    @Expose
    long homeScorePoints ;

    // display result as string for easy display.
    @Expose
    String homeScoreString ;
    @Expose
    String awayScoreString ;

    // as above but with points tally shown
    @Expose
    String homeScoreStringWithPointsTotal ;
    @Expose
    String awayScoreStringWithPointsTotal ;

    public void setHomeTeam(String homeTeam) {
        this.homeTeam = homeTeam;
    }

    public void setAwayTeam(String awayTeam) {
        this.awayTeam = awayTeam;
    }

    public void setHomePoints(long homePoints) {
        this.homePoints = homePoints;
    }

    public void setHomeGoals(long homeGoals) {
        this.homeGoals = homeGoals;
    }

    public void setAwayPoints(long awayPoints) {
        this.awayPoints = awayPoints;
    }

    public void setAwayGoals(long awayGoals) {
        this.awayGoals = awayGoals;
    }

    public void setAwayScorePoints(long awayScorePoints) {
        this.awayScorePoints = awayScorePoints;
    }

    public void setHomeScorePoints(long homeScorePoints) {
        this.homeScorePoints = homeScorePoints;
    }

    public void setHomeScoreString(String homeScoreString) {
        this.homeScoreString = homeScoreString;
    }

    public void setAwayScoreString(String awayScoreString) {
        this.awayScoreString = awayScoreString;
    }

    public void setHomeScoreStringWithPointsTotal(String homeScoreStringWithPointsTotal) {
        this.homeScoreStringWithPointsTotal = homeScoreStringWithPointsTotal;
    }

    public void setAwayScoreStringWithPointsTotal(String awayScoreStringWithPointsTotal) {
        this.awayScoreStringWithPointsTotal = awayScoreStringWithPointsTotal;
    }

    public String getHomeScore() {
        return String.format("%d - %d", homeGoals, homePoints);
    }
    public String getAwayScore() {
        return String.format("%d - %d", awayGoals, awayPoints);
    }
    public String getHomeScoreWithPointsTotal() {
        return String.format("%d - %d (%d)", homeGoals, homePoints, homeGoals * 3 + homePoints);
    }
    public String getAwayScoreWithPointsTotal() {
        return String.format("%d - %d (%d)", awayGoals, awayPoints, awayGoals * 3 + awayPoints);
    }
}
