package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;

public class ReservedRoomMoreInfo extends AppCompatActivity {
    private String apiURL = "http://10.0.2.2/get-images.php?rId=";
    private ImageURLData[] imageURLs;
    private SliderView sliderView;
    private Gson gson;
    private Intent intent;
    private String rId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_room_more_info);
        intent = getIntent();
        rId = intent.getStringExtra("rId");

        sliderView = findViewById(R.id.slSlider);

        gson = new Gson();
        getImageURLs();


    }

    private void getImageURLs() {
        apiURL += rId;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                System.out.println(response.toString());
                imageURLs = new ImageURLData[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        imageURLs[i] = gson.fromJson(response.getJSONObject(i).toString(), ImageURLData.class);
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                populateSlider();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        }) {
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }

    private void populateSlider() {
        ReservedRoomMoreInfoAdapter adapter = new ReservedRoomMoreInfoAdapter(this, imageURLs);

        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setSliderAdapter(adapter);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();
    }

    public void requestServices(View view) {

    }

    public void checkInOut(View view) {

    }
}