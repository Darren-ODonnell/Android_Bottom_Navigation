package com.example.bottomnavigationproper.Models;

public class Player {
    String id, firstname, lastname, firstnameI, lastnameI;
    int yob;
    String address;
    String email;
    String phone;
    String phoneIce;
    String registered;
    String grade;
    String availability;

    public Player() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public String getRegistered() {
        return registered;
    }

    public void setRegistered(String registered) {
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

    @Override
    public String toString() {
        return "Player{" +
                "id='" + id + '\'' +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", firstnameI='" + firstnameI + '\'' +
                ", lastnameI='" + lastnameI + '\'' +
                ", yob=" + yob +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneIce='" + phoneIce + '\'' +
                ", registered='" + registered + '\'' +
                ", grade='" + grade + '\'' +
                ", availability='" + availability + '\'' +
                '}';
    }
}
