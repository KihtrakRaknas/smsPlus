package com.example.karthik.studentmessagingservicesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    Intent intentSignedOut;
    Intent intentChat;
    DrawerLayout mDrawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intentSignedOut = new Intent(this,landing.class);
        intentChat = new Intent(this,ChatMessage.class);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                // set item as selected to persist highlight
                menuItem.setChecked(true);

                // Add code here to update the UI based on the item selected
                // For example, swap UI fragments here
                Log.d("yeet",""+menuItem.getItemId());
                if(menuItem.getItemId()==R.id.nav_sign_out){
                    FirebaseAuth.getInstance().signOut();
                    startActivity(intentSignedOut);
                }
                // close drawer when item is tapped
                //mDrawerLayout.closeDrawers();
                return true;
            }
        });
        if(user == null){
            startActivity(intentSignedOut);
        }else{
            ((TextView)headerView.findViewById(R.id.nav_view_name)).setText(user.getDisplayName());
            intentChat.putExtra("chat","TESTERS");
            startActivity(intentChat);
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
    }

    public void signOut(View v){

    }
}
