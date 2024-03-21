package com.example.quizapp.model;

public class TestModel {
    private  String testID;
    private int topScore;
    private int time;

    private String start;

    public String getTestID() {
        return testID;
    }

    public void setTestID(String testID) {
        this.testID = testID;
    }

    public int getTopScore() {
        return topScore;
    }

    public void setTopScore(int topScore) {
        this.topScore = topScore;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }



    public TestModel(String testID, int topScore, int time) {
        this.testID = testID;
        this.topScore = topScore;
        this.time = time;
    }
    public TestModel(String testID, int topScore, int time,String start) {
        this.testID = testID;
        this.topScore = topScore;
        this.time = time;
        this.start=start;

    }
}
