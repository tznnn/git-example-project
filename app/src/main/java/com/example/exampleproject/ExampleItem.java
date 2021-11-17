package com.example.exampleproject;

import android.net.Uri;

public class ExampleItem {
    private String name;
    private String surname;
    private String date;
    private String phoneNumber;
    private int genderId;
    private String imageProfile;
    private String checkboxAccountType;
    private String accountInfo;
    private int contractState;

    public ExampleItem(String name, String surname, String date, String phoneNumber, int genderId, String imageProfile, String checkboxAccountType, String accountInfo, int contractState) {
        this.name = name;
        this.surname = surname;
        this.date = date;
        this.phoneNumber = phoneNumber;
        this.genderId = genderId;
        this.imageProfile = imageProfile;
        this.checkboxAccountType = checkboxAccountType;
        this.accountInfo = accountInfo;
        this.contractState = contractState;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getGenderId() {
        return genderId;
    }

    public void setGenderId(int genderId) {
        this.genderId = genderId;
    }

    public String getImageProfile() {
        return imageProfile;
    }

    public void setImageProfile(String imageProfile) {
        this.imageProfile = imageProfile;
    }

    public String getCheckboxAccountType() {
        return checkboxAccountType;
    }

    public void setCheckboxAccountType(String checkboxAccountType) {
        this.checkboxAccountType = checkboxAccountType;
    }

    public String getAccountInfo() {
        return accountInfo;
    }

    public void setAccountInfo(String accountInfo) {
        this.accountInfo = accountInfo;
    }

    public int getContractState() {
        return contractState;
    }

    public void setContractState(int contractState) {
        this.contractState = contractState;
    }
}