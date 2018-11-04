package com.example.karthik.studentmessagingservicesms;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Intent intentSignedOut;
    Intent intentChat;
    DrawerLayout mDrawerLayout;

    ArrayAdapter<String> arrayAdapter;

    List<String> chats = new ArrayList<String>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
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
            myRef = database.getReference("message");
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        chats.add(snapshot.getKey());

                    }
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, chats);
            final ListView lv = findViewById(R.id.List);
            lv.setAdapter(arrayAdapter);

            lv.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intentChat.putExtra("chat",chats.get(position));
                    startActivity(intentChat);
                }
            });

            ((TextView)headerView.findViewById(R.id.nav_view_name)).setText(user.getDisplayName());
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();
    }

    public void signOut(View v){

    }
}
