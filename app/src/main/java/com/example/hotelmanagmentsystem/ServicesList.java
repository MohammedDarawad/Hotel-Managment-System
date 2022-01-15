package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hotelmanagmentsystem.model.CaptionedImagesAdapter;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.model.Service;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class ServicesList extends AppCompatActivity {
    private String apiURL = "http://10.0.2.2/get-services.php?type=";
    private Intent intent;
    private Gson gson;
    private Service[] servicesList;
    private RecyclerView rvServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);
        rvServicesList = findViewById(R.id.rvRequests);

        gson = new Gson();
        intent = getIntent();

        apiURL += intent.getStringExtra("type");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                servicesList = new Service[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        servicesList[i] = gson.fromJson(response.getJSONObject(i).toString(), Service.class);
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                rvServicesList.setLayoutManager(new LinearLayoutManager(ServicesList.this));
                CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(servicesList, ServicesList.this, intent.getStringExtra("rId"));
                rvServicesList.setAdapter(adapter);
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