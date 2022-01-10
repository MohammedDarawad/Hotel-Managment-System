package com.example.hotelmanagmentsystem;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.model.Room;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ReservedRoomMoreInfo extends AppCompatActivity {
    private String RoomApiURL = "http://10.0.2.2/get-room-info.php?rId=";
    private String imageApiURL = "http://10.0.2.2/get-images.php?rId=";
    private ImageURLData[] imageURLs;
    private SliderView sliderView;
    private Gson gson;
    private Intent intent;
    private String rId;
    private Button btCheckInOut;
    private TextView tvRoomNumber;
    private TextView tvFloor;
    private TextView tvRoomType;
    private TextView tvRoomCapacity;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private Boolean isCheckedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserved_room_more_info);
        intent = getIntent();
        gson = new Gson();

        rId = intent.getStringExtra("rId");

        sliderView = findViewById(R.id.slSlider);
        btCheckInOut = findViewById(R.id.btCheckInOut);

        tvRoomNumber = findViewById(R.id.tvRoomNumber);

        tvFloor = findViewById(R.id.tvRoomFloor);
        tvRoomType = findViewById(R.id.tvRoomType);
        tvRoomCapacity = findViewById(R.id.tvRoomCapacity);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);

        getReservationInformation();
        getImageURLs();
        getRoomInformation();
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

    public void requestServices(View view) {
        Intent intent = new Intent(this, ServicesList.class);
        intent.putExtra("rId", rId);
        startActivity(intent);
    }

    public void checkInOut(View view) {
        if (isCheckedIn) {
            new AlertDialog.Builder(this)
                    .setTitle("Conformation")
                    .setMessage("Are you sure you want to check out\n You wont be able to check in again")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // Continue with delete operation
                        }
                    })
                    .setNegativeButton(android.R.string.no, null)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        } else {
            btCheckInOut.setText("Check Out");
            btCheckInOut.setBackgroundColor(Color.RED);
            Toast.makeText(this, "Checked In Successfully", Toast.LENGTH_SHORT).show();
        }
    }

    private void getReservationInformation() {
        isCheckedIn = intent.getBooleanExtra("isCheckedIn", false);
        //System.out.println(isCheckedIn);
        if (isCheckedIn) {
            btCheckInOut.setText("Check Out");
            btCheckInOut.setBackgroundColor(Color.RED);
        }
        tvStartDate.setText(intent.getStringExtra("startDate"));
        tvEndDate.setText(intent.getStringExtra("endDate"));
    }

    private void getRoomInformation() {
        RoomApiURL += rId;
        StringRequest request = new StringRequest(Request.Method.GET, RoomApiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responceJsonObject = new JSONObject(response);
                    Room r = gson.fromJson(responceJsonObject.toString(), Room.class);
                    tvRoomNumber.setText(r.getrId() + "");
                    tvFloor.setText(r.getFloor() + "");
                    tvRoomType.setText(r.getType());
                    tvRoomCapacity.setText(r.getCapacity() + "");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });
        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }
}