package com.example.quizapp.model;

import java.util.ArrayList;
import java.util.List;

public class QuestionsModel {
    private String question;
    private String optionA;
    private String optionB;
    private String optionC;
    private String optionD;
    private int correctAns,correctAns2;
    private int selectedAns,selectAns2;
    private int status;

    public QuestionsModel(String question, String optionA, String optionB, String optionC, String optionD, int correctAns, int selectedAns, int status, int answer2, int i, int notVisited) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAns = correctAns;
        this.selectedAns = selectedAns;
        this.status = status;
    }
    public QuestionsModel(String question, String optionA, String optionB, String optionC, String optionD, int correctAns,int correctAns2, int selectedAns,int selectAns2, int status,int answer2, int i, int notVisited) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAns = correctAns;
        this.correctAns2 = correctAns2;
        this.selectedAns = selectedAns;
        this.selectAns2=selectAns2;
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSelectedAns() {
        return selectedAns;
    }

    public void setSelectedAns(int selectedAns) {
        this.selectedAns = selectedAns;
    }

    public int getSelectedAns2() {
        return selectAns2;
    }

    public void setSelectedAns2(int selectedAns2) {
        this.selectAns2 = selectedAns2;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getOptionA() {
        return optionA;
    }

    public void setOptionA(String optionA) {
        this.optionA = optionA;
    }

    public String getOptionB() {
        return optionB;
    }

    public void setOptionB(String optionB) {
        this.optionB = optionB;
    }

    public String getOptionC() {
        return optionC;
    }

    public void setOptionC(String optionC) {
        this.optionC = optionC;
    }

    public String getOptionD() {
        return optionD;
    }

    public void setOptionD(String optionD) {
        this.optionD = optionD;
    }

    public int getCorrectAns() {
        return correctAns;
    }

    public void setCorrectAns(int correctAns) {
        this.correctAns = correctAns;
    }
}
