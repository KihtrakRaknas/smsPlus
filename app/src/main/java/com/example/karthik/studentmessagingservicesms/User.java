package com.example.karthik.studentmessagingservicesms;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.ArrayList;
import java.util.List;

public class User {
    public String name;
    public String profile;
    public String school;
    public String messagingUID;
    public List<String> blocks;
    public List<Integer> UnsedBlocks= new ArrayList<Integer>();
    public User(){
        for(int i =0;i!=8;i++)
            UnsedBlocks.add(i);
    }

    public User(String name,String profile, String school, List<String>  blocks){
        this.name = name;
        this.profile = profile;
        this.school=school;
        this.blocks = blocks;
        messagingUID = MyFirebaseInstanceIDService.token;
        for(int i =0;i!=8;i++)
            UnsedBlocks.add(i);
    }

    public boolean blockCheck(String block, String room){
        //Log.d("TESTYEET",blocks.toString());
        if(blocks.get(Integer.parseInt(block)).equals(room)) {
            for(int i = UnsedBlocks.size()-1;i!=-1;i--)
                if(UnsedBlocks.get(i)==Integer.parseInt(block))
                    UnsedBlocks.remove(i);
            return true;
        }
        return false;
    }
}
