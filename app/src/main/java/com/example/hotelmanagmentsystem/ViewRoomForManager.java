package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.pacageViewRoom.ViewRoomAdapter;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewRoomForManager extends AppCompatActivity {
    private RecyclerView recycler;
    private RequestQueue queue;
    private RequestQueue queue2;
    private Gson gson;
    private int i=1;
    private ImageURLData[] imageURLs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_room_for_manager);
        recycler=findViewById(R.id.view_room_recycler);
        queue = Volley.newRequestQueue(this);
        queue2 = Volley.newRequestQueue(this);
        getData();
    }

    ArrayList<String> caption1 = new ArrayList<>();
    private void getData() {
        String url = "http://10.0.2.2:80/room-all-info.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        caption1.add( "Room ID: " + obj.getString("rId") + "\n"
                                +"Capacity: " + obj.getString("capacity")
                              );
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                recycler.setLayoutManager(new LinearLayoutManager(ViewRoomForManager.this));
                ViewRoomAdapter adapter = new ViewRoomAdapter(caption1, getApplicationContext());
                recycler.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ViewRoomForManager.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }

}
