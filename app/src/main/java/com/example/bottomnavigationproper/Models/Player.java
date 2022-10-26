package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Player {
    @SerializedName("id")
    @Expose
    Long id;
    @SerializedName("firstname")
    @Expose
    String firstname;
    @SerializedName("lastname")
    @Expose
    String lastname;
    @SerializedName("firstnameI")
    @Expose
    String firstnameI;
    @SerializedName("lastnameI")
    @Expose
    String lastnameI;
    @SerializedName("yob")
    @Expose
    int yob;
    @SerializedName("address")
    @Expose
    String address;
    @SerializedName("email")
    @Expose
    String email;
    @SerializedName("phone")
    @Expose
    String phone;
    @SerializedName("phoneIce")
    @Expose
    String phoneIce;
    @SerializedName("registered")
    @Expose
    boolean registered;
    @SerializedName("grade")
    @Expose
    String grade;
    @SerializedName("availability")
    @Expose
    String availability;
    @SerializedName("panelMember")
    @Expose
    Byte panelMember;

    public Player() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getFirstnameI() {
        return firstnameI;
    }

    public void setFirstnameI(String firstnameI) {
        this.firstnameI = firstnameI;
    }

    public String getLastnameI() {
        return lastnameI;
    }

    public void setLastnameI(String lastnameI) {
        this.lastnameI = lastnameI;
    }

    public int getYob() {
        return yob;
    }

    public void setYob(int yob) {
        this.yob = yob;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneIce() {
        return phoneIce;
    }

    public void setPhoneIce(String phoneIce) {
        this.phoneIce = phoneIce;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public Byte getPanelMember() {
        return panelMember;
    }

    public void setPanelMember(Byte panelMember) {
        this.panelMember = panelMember;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
