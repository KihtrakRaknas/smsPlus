package com.example.karthik.studentmessagingservicesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    Intent intentSignedOut;
    TextView text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentSignedOut = new Intent(this,ChatMessage.class);
        text = findViewById(R.id.id_text);
        startActivity(intentSignedOut);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        text.setText(auth.getUid());
    }

    public void signUp(View v){

    }
}
