package com.example.hotelmanagmentsystem;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.model.CaptionedImagesAdapter;
import com.example.hotelmanagmentsystem.model.Service;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class ServicesList extends AppCompatActivity {
    private final String apiURL = "http://10.0.2.2/get-services.php";
    private Gson gson;
    private Service[] servicesList;
    private RecyclerView rvServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services_list);
        rvServicesList = findViewById(R.id.rlServicesList);


        gson = new Gson();

        RequestQueue queue = Volley.newRequestQueue(ServicesList.this);

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
                CaptionedImagesAdapter adapter = new CaptionedImagesAdapter(servicesList,getApplicationContext());
                rvServicesList.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        });

        queue.add(request);
    }
}