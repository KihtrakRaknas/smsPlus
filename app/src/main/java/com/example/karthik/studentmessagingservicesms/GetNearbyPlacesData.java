package com.example.karthik.studentmessagingservicesms;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData extends AsyncTask<Object, String, String> {

    String googlePlacesData;
    String url;
    ArrayList<String> list = new ArrayList<>();

    @Override
    protected String doInBackground(Object... params) {
        try {
            Log.d("GetNearbyPlacesData", "doInBackground entered");
            url = (String) params[0];
            DownloadUrl downloadUrl = new DownloadUrl();
            googlePlacesData = downloadUrl.readUrl(url);
        } catch (Exception e) {
            Log.d("GooglePlacesReadTask", e.toString());
        }
        return googlePlacesData;
    }

    @Override
    protected void onPostExecute(String result) {
        Log.d("GooglePlacesReadTask", "onPostExecute Entered");
        //List<HashMap<String,String>> nearbyPlacesList;
        //DataParser dataParser = new DataParser();
        //Log.d("ylyl", result);
        //nearbyPlacesList =  dataParser.parse(googlePlacesData);
        //Log.d("GooglePlacesReadTask", nearbyPlacesList.toString());
        for (int x = 0; x < googlePlacesData.length(); x++) {
            if (googlePlacesData.contains("\"name\" : ")) {
                for (int y = x + 11; y < googlePlacesData.length(); y++) {
                    if (googlePlacesData.charAt(y) == '\"')
                        list.add(googlePlacesData.substring(x + 10, y));
                }
            }
            Log.d("pleasework", list.get(0));
        }
    }
}
