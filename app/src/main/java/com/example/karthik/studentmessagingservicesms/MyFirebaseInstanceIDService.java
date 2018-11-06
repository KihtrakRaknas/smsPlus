package com.example.karthik.studentmessagingservicesms;

import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;

public class MyFirebaseInstanceIDService extends FirebaseMessagingService {

    static String token;
    @Override
    public void onNewToken(String s) {
        super.onNewToken(s);
        token = s;
        Log.d("TokenYEEt",s);
    }

    public MyFirebaseInstanceIDService() {
        super();
    }

}
