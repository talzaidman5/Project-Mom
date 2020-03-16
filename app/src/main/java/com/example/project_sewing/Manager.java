package com.example.project_sewing;

public class Manager {

private String phoneNumber;
private String password;
private String id;

    public Manager(String phoneNumber, String password, String id) {
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.id = id;
    }
    public Manager(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }


    public String getPassword() {
        return password;
    }

    public String getId() {
        return id;
    }
}
