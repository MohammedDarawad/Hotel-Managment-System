package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.model.ReservedRoom;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class ReserveList extends AppCompatActivity {

    private Intent intent;
    private String apiURL = "http://10.0.2.2/get-reserved-rooms.php";
    private RecyclerView rvReservedRoomsList;
    private TextView tvError;
    private Gson gson;
    private ReservedRoom[] reservedRoomsList;
    private String uId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);

        rvReservedRoomsList = findViewById(R.id.rvReservdRoomsList);
        tvError = findViewById(R.id.tvError);
        intent = getIntent();

        gson = new Gson();
        try {
            uId = intent.getStringExtra("uId");
            apiURL += "?uId=" + uId;

            RequestQueue queue = Volley.newRequestQueue(ReserveList.this);
            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiURL,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    reservedRoomsList = new ReservedRoom[response.length()];
                    System.out.println(response.toString());
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            reservedRoomsList[i] = gson.fromJson(response.getJSONObject(i).toString(), ReservedRoom.class);
                            System.out.println(reservedRoomsList[i].isCheckedIn() + " test");
                        } catch (JSONException exception) {
                            Log.d("Error", exception.toString());
                        }
                    }
//                    if (reservedRoomsList.length != 0) {
//                        rvReservedRoomsList.setLayoutManager(new LinearLayoutManager(ReserveList.this));
//                        reservedRoomsAdapter adapter = new reservedRoomsAdapter(reservedRoomsList, getApplicationContext());
//                        rvReservedRoomsList.setAdapter(adapter);
//                    } else {
//                        rvReservedRoomsList.setVisibility(View.GONE);
//                        tvError.setVisibility(View.VISIBLE);
//                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error.toString());
                }
            });

            RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
        } catch (Exception ex) {
            Log.d("Error", ex.toString());
            ex.printStackTrace();
        }
    }
}