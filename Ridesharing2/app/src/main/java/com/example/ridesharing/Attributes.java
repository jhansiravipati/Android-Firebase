package com.example.ridesharing;

public class Attributes {
    String name,date,from,to;

    public String getName() {
        return name;
    }

    public Attributes(String name, String date, String from, String to) {
        this.name = name;
        this.date = date;
        this.from = from;
        this.to = to;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}
