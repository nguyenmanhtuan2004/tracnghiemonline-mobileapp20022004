package com.example.quizapp.model;

public class RankModel {
    private int score;
    private int rank;
    private String name ;

    public RankModel(String name, int score, int rank) {
        this.score = score;
        this.rank = rank;
        this.name = name ;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
    }
}
