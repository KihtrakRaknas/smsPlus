package com.example.karthik.studentmessagingservicesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {
    Intent signUp;
    Intent signIn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signUp = new Intent(this,phoneORuser.class);
        signIn = new Intent(this, LoginActivity.class);
    }

    public void redirectToSignIn(View V){
        startActivity(signIn);
    }

    public void redirectToSignUp(View V){
        startActivity(signUp);
    }
}