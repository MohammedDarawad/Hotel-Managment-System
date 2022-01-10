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

import java.util.HashMap;
import java.util.Map;

public class ReservedRoomMoreInfo extends AppCompatActivity {
    private final String checkInApiURL = "http://10.0.2.2/set-checkin.php";
    private String RoomApiURL = "http://10.0.2.2/get-room-info.php?rId=";
    private String imageApiURL = "http://10.0.2.2/get-images.php?rId=";
    private ImageURLData[] imageURLs;
    private SliderView sliderView;
    private Gson gson;
    private Intent intent;
    private String rId;
    private int reservationId;
    private Button btCheckInOut;
    private TextView tvRoomNumber;
    private TextView tvFloor;
    private TextView tvRoomType;
    private TextView tvRoomCapacity;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private int isCheckedIn;
    private int uId;

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
        if (isCheckedIn == 1) {
            handleCheckOut();
        } else {
            handleCheckIn();
        }
    }

    private void handleCheckOut() {
        new AlertDialog.Builder(this)
                .setTitle("Conformation")
                .setMessage("Are you sure you want to check out\n You wont be able to check in again")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        StringRequest request = new StringRequest(Request.Method.POST, checkInApiURL, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject responceJsonObject = new JSONObject(response);
                                    if (responceJsonObject.has("hasError")) {
                                        if (!responceJsonObject.getBoolean("hasError")) {
                                            finish();
                                        } else {
                                            Toast.makeText(ReservedRoomMoreInfo.this,
                                                    responceJsonObject.getString("message"), Toast.LENGTH_LONG).show();
                                        }
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Log.d("Error", error.toString());
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("id", reservationId + "");
                                params.put("val", "0");
                                return params;
                            }

                            @Override
                            public String getBodyContentType() {
                                return "application/x-www-form-urlencoded; charset=UTF-8";
                            }
                        };

                        RequestQueueSingleton.getInstance(ReservedRoomMoreInfo.this).addToRequestQueue(request);
                    }
                })
                .setNegativeButton(android.R.string.no, null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    private void handleCheckIn() {
        StringRequest request = new StringRequest(Request.Method.POST, checkInApiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responceJsonObject = new JSONObject(response);
                    if (responceJsonObject.has("hasError")) {
                        if (!responceJsonObject.getBoolean("hasError")) {
                            btCheckInOut.setText("Check Out");
                            btCheckInOut.setBackgroundColor(Color.RED);
                            Toast.makeText(ReservedRoomMoreInfo.this, "Checked In Successfully", Toast.LENGTH_SHORT).show();
                            isCheckedIn = 1;
                        } else {
                            Toast.makeText(ReservedRoomMoreInfo.this,
                                    responceJsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("id", reservationId + "");
                params.put("val", "1");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }

    private void getReservationInformation() {
        isCheckedIn = intent.getIntExtra("isCheckedIn", 0);
        if (isCheckedIn == 1) {
            btCheckInOut.setText("Check Out");
            btCheckInOut.setBackgroundColor(Color.RED);
        }
        reservationId = intent.getIntExtra("id", 0);
        uId = intent.getIntExtra("uId", 0);
        tvStartDate.setText(intent.getStringExtra("startDate"));
        tvEndDate.setText(intent.getStringExtra("endDate"));
    }

    private void getRoomInformation() {
        RoomApiURL += rId;
        StringRequest request = new StringRequest(Request.Method.GET, RoomApiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Room r = gson.fromJson(response, Room.class);
                tvRoomNumber.setText(r.getrId() + "");
                tvFloor.setText(r.getFloor() + "");
                tvRoomType.setText(r.getType());
                tvRoomCapacity.setText(r.getCapacity() + "");
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