package com.example.hotelmanagmentsystem.packageRoomForManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.ReservedRoomMoreInfoAdapter;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;

public class ViewRoomMoreInfoForManager extends AppCompatActivity {
    private SliderView sliderView;
    private String imageApiURL = "http://10.0.2.2/get-images.php?rId=";
    private TextView txtInfo;
    private String type;
    private int floor;
    private int rId;
    private int isReserved;
    private int capacity;
    private Intent intent;
    private ImageURLData[] imageURLs;
    private Gson gson;
    private String reserved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_more_info_for_manager);
        sliderView = findViewById(R.id.RSlider);
        txtInfo = findViewById(R.id.txtAllInfo);
        intent = getIntent();
        gson=new Gson();
        rId =intent.getIntExtra("rId",0);
        floor = intent.getIntExtra("floor",0);
        isReserved = intent.getIntExtra("isReserved",0);
        type=intent.getStringExtra("type");
        capacity=intent.getIntExtra("capacity",0);
        getRoomInfo();
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
    private void getRoomInfo(){
        if(isReserved==1){
            reserved = "its reserved";
        }
        else {
            reserved = "its not reserved";
        }
        txtInfo.setText("Room Number " +rId+"" + "\n" + "Floor Number " + floor+"" + "\n" + reserved + "\n" +
                "Room Type is " + type + "\n" + "Room Capacity: " + capacity+"");
    }

    public void btnOnClickEdit(View view) {
        Intent intent = new Intent(this, EditRoomForManager.class);
        intent.putExtra("rId",rId );
        intent.putExtra("type",type);
        intent.putExtra("capacity", capacity);
        startActivity(intent);
    }
    public void btnOnClickBack(View view) {
        Intent intent = new Intent(this, ViewRoomForManager.class);
        startActivity(intent);
    }
}