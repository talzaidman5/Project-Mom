package com.example.project_sewing;

public class Massage  {
    private String phoneNumber;
    private String massages;


    public Massage(int day, int month, int year, String hour,String phoneNumber,String status) {
        this.phoneNumber = phoneNumber;
        this.massages = "- Your meeting on "+day+"/"+month+"/"+year+ " at "+ hour+ status;
    }
    public Massage(){}

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getMassages() {
        return massages;
    }

    public void setMassages(String massages) {
        this.massages = massages;
    }
}
