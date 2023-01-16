package com.example.bottomnavigationproper.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Player implements Serializable {
    @Expose
    Long id;
    @Expose
    String firstname;
    @Expose
    String lastname;
    @Expose
    String firstnameI;
    @Expose
    String lastnameI;
    @Expose
    int yob;
    @Expose
    String address;
    @Expose
    String email;
    @Expose
    String phone;
    @Expose
    String phoneIce;
    @Expose
    boolean registered;
    @Expose
    String grade;
    @Expose
    String availability;
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

    public String getAbbrevName(){
        return firstname.charAt(0) + ". " + lastname;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}
