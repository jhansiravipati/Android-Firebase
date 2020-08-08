package com.example.searchfirebase;

public class Model {
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    private String question;

    public Model(String question) {
        this.question = question;
    }

    public Model() {
    }
}
