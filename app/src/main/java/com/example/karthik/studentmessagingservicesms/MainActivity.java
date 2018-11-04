package com.example.karthik.studentmessagingservicesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Intent intentSignedOut;
    Intent intentChat;
    TextView text;
    private DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentSignedOut = new Intent(this,landing.class);
        intentChat = new Intent(this,ChatMessage.class);
        text = findViewById(R.id.id_text);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);
                // close drawer when item is tapped
                mDrawerLayout.closeDrawers();

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here

                return true;
            }
        });
        if(user == null){
            startActivity(intentSignedOut);
        }else{
            startActivity(intentChat);
            ((TextView)headerView.findViewById(R.id.nav_view_name)).setText(user.getDisplayName());
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
        text.setText(auth.getUid());
    }

    public void signUp(View v){

    }
}
