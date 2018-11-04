package com.example.karthik.studentmessagingservicesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class school_class_selector extends AppCompatActivity{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    List<String> blocks = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.school_class_selector);
        myRef = database.getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ((EditText)findViewById(R.id.block1)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                blocks.set(0,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        myRef = database.getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ((EditText)findViewById(R.id.block2)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                blocks.set(1,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        myRef = database.getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ((EditText)findViewById(R.id.block3)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                blocks.set(2,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        myRef = database.getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ((EditText)findViewById(R.id.block4)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                blocks.set(3,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ((EditText)findViewById(R.id.block5)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                blocks.set(4,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        myRef = database.getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ((EditText)findViewById(R.id.block6)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                blocks.set(5,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        myRef = database.getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ((EditText)findViewById(R.id.block7)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                blocks.set(6,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        myRef = database.getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
        ((EditText)findViewById(R.id.block8)).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                blocks.set(7,s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    public void sumbit(View v){
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String school = intent.getStringExtra("school");
        String profile = intent.getStringExtra("profile");
        User user = new User(name,profile,school,blocks);
        myRef.setValue(user);
    }
}
