package com.example.backendapp;

public class Attributes {

    public static String from;
    public String to;
    public String gender;
    public static long rating;

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String review;




    public long getRating() {
        return rating;
    }

    public float setRating(long rating) {
        this.rating = rating;
        return rating;
    }


    public String getMode_of_travel() {
        return mode_of_travel;
    }

    public void setMode_of_travel(String mode_of_travel) {
        this.mode_of_travel = mode_of_travel;
    }

    private String mode_of_travel;

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }



    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }



    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }


    public Attributes() {

    }


    public Attributes(String from,String to) {
        this.from = from;
        this.to=to;
    }
}
