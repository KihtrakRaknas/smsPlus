package com.example.karthik.studentmessagingservicesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class SchoolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_school);
    }

    public void nexter(View v){
        Intent intent = new Intent(SchoolActivity.this, ProfileSetUp.class);
        intent.putExtra("school","South Brunswick High School");
        startActivity(intent);
    }
}
