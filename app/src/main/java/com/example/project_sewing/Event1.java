package com.example.project_sewing;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class Event1 {
    private String number;
    private String type;
    private int price;
    private int day;
    private int year;
    private int month;
    private String hour;
    private int status;

    public Event1(String number, String type, int price, int day, int month, int year, String hour,int status) {
        this.number = number;
        this.type = type;
        this.price = calculationCost();
        this.day = day;
        this.year = year;
        this.month = month;
        this.hour = hour;
        this.status = status;
    }

    public String getNumber() {
        return number;
    }

    public Event1(){}


    public String getType() {
        return type;
    }

    public String getHour() {
        return hour;
    }

    public int getYear() {
        return year;
    }

    public int getStatus() {
        return status;
    }

    public int getDay() {
        return day;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getMonth() {
        return month;
    }


    public int calculationCost(){
        int cost=0;
        switch (type){

            case ("Hem a skirt or dress"):
                cost += 30;
                break;
            case ("Shorten pants"):
                cost += 25;
                break;
            case ("Shorten dress-shirt sleeves"):
                cost += 55;
                break;
            case ("Zipper replacement"):
                cost += 40;
                break;

            case ("Add a button"):
                cost += 20;
                break;
        }
        return cost;

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event1)) return false;
        Event1 event1 = (Event1) o;
        return  day == event1.day &&
                year == event1.year &&
                month == event1.month &&
                hour == event1.hour &&
                Objects.equals(hour, event1.hour);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public int hashCode() {
        return Objects.hash(number, type, day, year, month);
    }

    @Override
    public String toString() {
        return "Event1{" +
                "number='" + number + '\'' +
                ", type='" + type + '\'' +
                ", day=" + day +
                ", year=" + year +
                ", month=" + month +
                ", hour='" + hour + '\'' +
                '}';
    }
}
