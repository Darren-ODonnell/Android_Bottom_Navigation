package com.example.bottomnavigationproper.Models;


import com.google.gson.annotations.Expose;

public class Fellowship {
    @Expose()
    private Long id;
    @Expose()
    private String firstname;
    @Expose()
    private String lastname;
    @Expose()
    private String firstnameI;
    @Expose()
    private String lastnameI;
    @Expose()
    private Integer yob;
    @Expose()
    private String address;
    @Expose()
    private String email;
    @Expose()
    private String phone;
    @Expose()
    private String phoneIce;
    @Expose()
    private String availability;
    @Expose()
    private String grade;
    @Expose()
    private Integer registered;
    @Expose()
    private Byte panelMember;
    @Expose()
    private String fellowType;


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

    public Integer getYob() {
        return yob;
    }

    public void setYob(Integer yob) {
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

    public String getAvailability() {
        return availability;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getRegistered() {
        return registered;
    }

    public void setRegistered(Integer registered) {
        this.registered = registered;
    }

    public Byte getPanelMember() {
        return panelMember;
    }

    public void setPanelMember(Byte panelMember) {
        this.panelMember = panelMember;
    }

    public String getFellowType() {
        return fellowType;
    }

    public void setFellowType(String fellowType) {
        this.fellowType = fellowType;
    }
}