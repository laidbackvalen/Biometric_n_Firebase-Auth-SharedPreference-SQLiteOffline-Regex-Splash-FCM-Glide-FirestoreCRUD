package com.example.firestorepracticejobinterview.model;

public class ModelClass {
    String name;
    String email;
    String phone;
    String url;
    String timeStamp;

    //Default Constructor
    public ModelClass() {
    }

    //Parameterized constructor
    public ModelClass(String name, String email, String phone, String url, String timeStamp ) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.url = url;
        this.timeStamp = timeStamp;

    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getUrl() {
        return url;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

}
