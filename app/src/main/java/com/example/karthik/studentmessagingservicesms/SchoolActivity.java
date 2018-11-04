package com.example.karthik.studentmessagingservicesms;


import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SchoolActivity extends AppCompatActivity {

        double latitude;
        double longitude;
        TextView school;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_school);
            school=findViewById(R.id.school);
            latitude=40.382118;
            longitude=-74.535278;
            Object[] DataTransfer = new Object[2];
            String url = getUrl(latitude, longitude, "school");
            DataTransfer[0]=url;
            DataTransfer[1]=url;
            GetNearbyPlacesData getNearbyPlacesData = new GetNearbyPlacesData();
            getNearbyPlacesData.execute(DataTransfer);
            Toast.makeText(com.example.karthik.studentmessagingservicesms.SchoolActivity.this,"Nearby Schools", Toast.LENGTH_LONG).show();
        }



        private String getUrl(double latitude, double longitude, String nearbyPlace) {

            StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
            googlePlacesUrl.append("location=" + latitude + "," + longitude);
            googlePlacesUrl.append("&radius=" + 1500);
            googlePlacesUrl.append("&type=" + nearbyPlace);
            googlePlacesUrl.append("&sensor=true");
            googlePlacesUrl.append("&key=" + "AIzaSyA50WcFe1tAIe-zCvFPN-RXXNaQI26Ouw4");
            Log.d("getUrl", googlePlacesUrl.toString());
            return (googlePlacesUrl.toString());
        }

        public void setText(String s){
            school.setText(s);
        }

    }

