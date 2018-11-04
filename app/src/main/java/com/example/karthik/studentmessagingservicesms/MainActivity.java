package com.example.karthik.studentmessagingservicesms;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Intent intentSignedOut;
    Intent intentChat;

    User userData=null;

    String school;

    Intent newChat;
    DrawerLayout mDrawerLayout;

    ArrayAdapter<String> arrayAdapter;
    ArrayAdapter adapter;

    List<String> chats = new ArrayList<String>();
    List<String> lastMessageInChat = new ArrayList<String>();
    List<String> lastMessageInChatTimeStamp = new ArrayList<String>();
    List<String> userTimeStamp = new ArrayList<String>();
    List<String> chatID = new ArrayList<String>();

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;

    Boolean first = true;

    DatabaseReference myUserRef;
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
            myUserRef= database.getReference("user/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
            myUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    userData = dataSnapshot.getValue(User.class);


                    school = "South Brunswick High School";
                    myRef = database.getReference("message/"+school);

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            while(chats.size()!=0) {
                                chats.remove(0);
                                lastMessageInChat.remove(0);
                                lastMessageInChatTimeStamp.remove(0);
                                userTimeStamp.remove(0);
                                chatID.remove(0);
                            }
                            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {


                                for (DataSnapshot user : snapshot.child("Members").getChildren()) {
                                    boolean keysmatch = user.getKey().equals(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    boolean blockmatch = userData.blockCheck(snapshot.child("block").getValue(String.class), snapshot.child("room").getValue(String.class));
                                    if(keysmatch||blockmatch){
                                        chats.add(snapshot.child("Name").getValue().toString());
                                        chatID.add(snapshot.getKey());
                                        lastMessageInChat.add(snapshot.child("Messages").child(""+(snapshot.child("Messages").getChildrenCount()-1)).child("messageUser").getValue().toString()+": "+snapshot.child("Messages").child(""+(snapshot.child("Messages").getChildrenCount()-1)).child("messageText").getValue().toString());
                                        lastMessageInChatTimeStamp.add(snapshot.child("Messages").child(""+(snapshot.child("Messages").getChildrenCount()-1)).child("messageTime").getValue().toString() );
                                        userTimeStamp.add(user.getValue().toString());
                                        break;
                                    }
                                }

                            }
                            //first = false;
                            if(first) {
                                first = false;
                                for (int i = 0; i != userData.UnsedBlocks.size(); i++) {
                                    String str = ("" + Math.random()).substring(2);
                                    DatabaseReference chat = database.getReference("message/" + school + "/" + str);
                                    chat.setValue(new chat("PERIOD " + userData.UnsedBlocks.get(i), "" + userData.UnsedBlocks.get(i), userData.blocks.get(userData.UnsedBlocks.get(i))));
                                    DatabaseReference chat2 = database.getReference("message/" + school + "/" + str+"/Messages/0");
                                    chat2.setValue(new indivMessage("HI " , "SERVER","SERVER"));
                                    DatabaseReference chat3 = database.getReference("message/" + school + "/" + str+"/Members/"+FirebaseAuth.getInstance().getCurrentUser().getUid());
                                    chat3.setValue(new Date().getTime());
                                }
                            }
                            adapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_2, android.R.id.text1, chats) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    text1.setText(chats.get(position));
                    text2.setText(lastMessageInChat.get(position));
                    Log.d("HELPYEET",lastMessageInChatTimeStamp.get(position)+";"+userTimeStamp.get(position));
                    if(Long.parseLong(lastMessageInChatTimeStamp.get(position)) > Long.parseLong(userTimeStamp.get(position))) {
                        text1.setTypeface(null, Typeface.BOLD);
                        text2.setTypeface(null, Typeface.BOLD);

                    }else{
                        text1.setTypeface(null, Typeface.NORMAL);
                        text2.setTypeface(null, Typeface.NORMAL);
                    }
                    return view;
                }
            };

            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_2, chats);
            final ListView lv = findViewById(R.id.List);
            //lv.setAdapter(arrayAdapter);
            lv.setAdapter(adapter);

            lv.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    intentChat.putExtra("chatID",chatID.get(position));
                    intentChat.putExtra("chat",chats.get(position));
                    startActivity(intentChat);
                }
            });

            ((TextView)headerView.findViewById(R.id.nav_view_name)).setText(user.getDisplayName());
        }


        newChat = new Intent(this, NewChat.class);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(newChat);
            }
        });



        FirebaseAuth auth = FirebaseAuth.getInstance();
        //intentChat.putExtra("chatID","TESTERS");
        //intentChat.putExtra("chat","TEST");
        //startActivity(intentChat);







    }

    public void signOut(View v){

    }

}
