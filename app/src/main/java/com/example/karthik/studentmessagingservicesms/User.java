package com.example.karthik.studentmessagingservicesms;

public class User {
    public String profile;
    public String School;
    public String blocks[];
    public User(){

    }

    public User(String profile, String school, String[] blocks){
        this.profile = profile;
        this.School=School;
        this.blocks = blocks;
    }
}
