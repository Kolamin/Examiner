package ru.anton.models;

public class Question {
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String question;
    private String[] testOptions;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getTestOptions() {
        return testOptions;
    }

    public void setTestOptions(String[] testOptions) {
        this.testOptions = testOptions;
    }
}
