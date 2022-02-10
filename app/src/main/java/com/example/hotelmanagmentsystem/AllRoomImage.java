package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

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

public class AllRoomImage extends AppCompatActivity {
    Intent intent = getIntent();
    String rId = intent.getStringExtra("rId");
    private SliderView sliderView;
    private String imageApiURL = "http://10.0.2.2/get-images.php?rId=";
    private ImageURLData[] imageURLs;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_room_image);
        sliderView = findViewById(R.id.slSlider);
        gson = new Gson();
        getImageURLs();

    }

    private void getImageURLs() {
        imageApiURL += rId + "&getAllImages=1";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, imageApiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
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
}