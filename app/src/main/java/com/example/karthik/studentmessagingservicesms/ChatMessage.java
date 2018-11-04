package com.example.karthik.studentmessagingservicesms;

import android.os.Bundle;

import java.util.Date;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
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

    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int maxIndex=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message);

        myRef = database.getReference("message");

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = findViewById(R.id.input);

                // Read the input field and push a new instance
                // of ChatMessage to the Firebase database
                indivMessage mess = new indivMessage(input.getText().toString(),
                        FirebaseAuth.getInstance()
                                .getCurrentUser()
                                .getDisplayName());
                myRef.child(""+(maxIndex+1)).setValue(mess);

                // Clear the input
                input.setText("");
            }
        });

        list = findViewById(R.id.list_messages);

        FirebaseUser user =  FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName("Franklin Yin").build();
        user.updateProfile(profileUpdates);

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                list.removeAllViews();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    int index = Integer.parseInt(snapshot.getKey());
                    if(index>maxIndex)
                    maxIndex=index;
                    indivMessage mess = snapshot.getValue(indivMessage.class);

                    TextView message = new TextView(ChatMessage.this);
                    message.setBackground(getDrawable(R.drawable.rounded_rectangle_orange));
                    ConstraintLayout.LayoutParams params = new ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.WRAP_CONTENT,ConstraintLayout.LayoutParams.WRAP_CONTENT);
                    message.setLayoutParams(params);
                    params.setMargins(10,10,10,10);
                    int paddingDp = 8;
                    float density = ChatMessage.this.getResources().getDisplayMetrics().density;
                    int paddingPixel = (int)(paddingDp * density);
                    message.setPadding(paddingPixel,paddingPixel,paddingPixel,paddingPixel);
                    message.setText(mess.messageText);

                    //list.addView(message);

                    TextView timestamp = new TextView(ChatMessage.this);
                    timestamp.setText(""+mess.messageTime);
                    timestamp.setLayoutParams(params);
                    timestamp.setTextSize(TypedValue.COMPLEX_UNIT_SP,10);




                    ConstraintLayout newLay = new ConstraintLayout(ChatMessage.this);
                    newLay.addView(message);
                    newLay.addView(timestamp);

                    ConstraintSet constraintSet = new ConstraintSet();
                    constraintSet.clone(newLay);
                    constraintSet.connect(timestamp.getId(), ConstraintSet.LEFT, message.getId(), ConstraintSet.RIGHT, 0);

                    LinearLayout.LayoutParams params2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
                    params2.setMargins(5,5,5,5);
                    newLay.setLayoutParams(params2);
                    //newLay.setPadding(paddingPixel,paddingPixel,paddingPixel,paddingPixel);

                    list.addView(newLay);
                }
                //String value = dataSnapshot.getValue(String.class);
                //Log.d("READDATA", "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("READDATA", "Failed to read value.", error.toException());
            }
        });

    }

}