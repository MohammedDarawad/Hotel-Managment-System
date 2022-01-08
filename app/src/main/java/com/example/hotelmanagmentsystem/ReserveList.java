package com.example.hotelmanagmentsystem;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.model.Room;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class ReserveList extends AppCompatActivity {

    private final String apiURL = "http://10.0.2.2/get-reserved-rooms.php";
    private RecyclerView rvReservedRoomsList;
    private Gson gson;
    private Room[] reservedRoomsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reserve_list);

        rvReservedRoomsList = findViewById(R.id.rvReservdRoomsList);

        gson = new Gson();

        RequestQueue queue = Volley.newRequestQueue(ReserveList.this);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                reservedRoomsList = new Room[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        reservedRoomsList[i] = gson.fromJson(response.getJSONObject(i).toString(), Room.class);
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
//              rvServicesList.setLayoutManager(new LinearLayoutManager(ServicesList.this));
                reservedRoomsAdapter adapter = new reservedRoomsAdapter(reservedRoomsList, getApplicationContext());
                rvReservedRoomsList.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });

        //TODO:make it a singleton class
        queue.add(request);
    }
}