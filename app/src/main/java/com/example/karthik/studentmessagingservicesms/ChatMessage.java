package com.example.karthik.studentmessagingservicesms;

import android.os.Bundle;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatMessage extends AppCompatActivity{
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef;
    LinearLayout list;
    FirebaseUser user;
    ScrollView scrollView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int maxIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        scrollView = findViewById(R.id.scroll);


        myRef = database.getReference("message");

        FloatingActionButton fab = findViewById(R.id.fab);
        user = FirebaseAuth.getInstance().getCurrentUser();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                indivMessage mess = new indivMessage(input.getText().toString(),user.getDisplayName(),user.getUid());
                myRef.child(""+(maxIndex+1)).setValue(mess);

                // Clear the input
                input.setText("");
            }
        });

        list = findViewById(R.id.list_messages);

        //UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName("Karthik Sankar").build();
        //user.updateProfile(profileUpdates);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.removeAllViews();
                indivMessage oldMess = null;
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int index = Integer.parseInt(snapshot.getKey());
                    if (index > maxIndex)
                        maxIndex = index;

                    indivMessage mess = snapshot.getValue(indivMessage.class);

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);



                    if ((oldMess == null || !oldMess.messageUser.equals(mess.messageUser)) && !user.getUid().equals(mess.UID)) {
                        TextView name = new TextView(ChatMessage.this);
                        name.setLayoutParams(params);
                        name.setText(mess.messageUser);
                        name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);
                        list.addView(name);
                    }

                    TextView message = new TextView(ChatMessage.this);
                    message.setBackground(getDrawable(R.drawable.rounded_rectangle_orange));
                    params.setMargins(10, 7, 7, 10);
                    int paddingDp = 8;
                    float density = ChatMessage.this.getResources().getDisplayMetrics().density;
                    int paddingPixel = (int) (paddingDp * density);
                    message.setPadding(paddingPixel, paddingPixel, paddingPixel, paddingPixel);
                    message.setText(mess.messageText);
                    if (user.getUid().equals(mess.UID)){
                        params.gravity = Gravity.RIGHT;
                        message.setBackground(getDrawable(R.drawable.rounded_rectangle_blue));
                    }
                    message.setLayoutParams(params);
                    list.addView(message);

                    if(oldMess == null || mess.messageTime-oldMess.messageTime > 60000) {
                        TextView timestamp = new TextView(ChatMessage.this);
                        Date date = new Date(mess.messageTime);
                        DateFormat formatter = new SimpleDateFormat("h:mm a");
                        String dateFormatted = formatter.format(date);
                        timestamp.setText(dateFormatted);
                        timestamp.setLayoutParams(params);
                        timestamp.setTextSize(TypedValue.COMPLEX_UNIT_SP, 8);

                        LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        params2.setMargins(50, 0, 50, 0);

                        if (user.getUid().equals(mess.UID)){
                            params2.gravity = Gravity.RIGHT;
                        }
                        timestamp.setLayoutParams(params2);
                        list.addView(timestamp);
                    }



                    oldMess = mess;
                }
                //String value = dataSnapshot.getValue(String.class);
                //Log.d("READDATA", "Value is: " + value);
                scrollView.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(ScrollView.FOCUS_DOWN);
                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("READDATA", "Failed to read value.", error.toException());
            }
        });

    }

}