package com.example.bottomnavigationproper.Models;

import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

public class StatsView implements Serializable, Parcelable {
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

    @RequiresApi(api = Build.VERSION_CODES.Q)
    protected StatsView(Parcel in) {
        statName = in.readString();
        success = in.readBoolean();
        half = in.readInt();
        season = in.readInt();
        firstName = in.readString();
        lastName = in.readString();
        homeTeam = in.readString();
        awayTeam = in.readString();
        location = in.readString();
        fixtureDate = in.readString();
        if (in.readByte() == 0) {
            timeOccurred = null;
        } else {
            String value = in.readString();
            timeOccurred = new BigDecimal(value);
        }
        count = in.readString();
    }

    public static final Creator<StatsView> CREATOR = new Creator<StatsView>() {
        @RequiresApi(api = Build.VERSION_CODES.Q)
        @Override
        public StatsView createFromParcel(Parcel in) {
            return new StatsView(in);
        }

        @Override
        public StatsView[] newArray(int size) {
            return new StatsView[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    public void writeToParcel(Parcel dest, int flags) {
         dest.writeString(statName);
         dest.writeBoolean(success);
         dest.writeInt(half);
         dest.writeInt(season);
         dest.writeString(firstName);
         dest.writeString(lastName);
         dest.writeString(homeTeam);
         dest.writeString(awayTeam);
         dest.writeString(location);
         dest.writeString(fixtureDate);
         dest.writeString(String.valueOf(timeOccurred));
         dest.writeString(count);
    }
}
