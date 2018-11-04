package com.example.karthik.studentmessagingservicesms;

import java.util.List;

public class User {
    public String name;
    public String profile;
    public String school;
    public List<String> blocks;
    public User(){

    }

    public User(String name,String profile, String school, List<String>  blocks){
        this.name = name;
        this.profile = profile;
        this.school=school;
        this.blocks = blocks;
    }
}
