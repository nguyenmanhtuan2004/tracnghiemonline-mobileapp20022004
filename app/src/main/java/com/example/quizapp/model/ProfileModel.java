package com.example.quizapp.model;

public class ProfileModel {

    private String name;
    private String email;
    private String phone;
    private String vaitro;

    public ProfileModel(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }
    public ProfileModel(String name, String email, String phone,String vaitro) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.vaitro=vaitro;
    }
    public ProfileModel() {
    }

    public String getVaitro() {
        return vaitro;
    }

    public void setVaitro(String vaitro) {
        this.vaitro = vaitro;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
