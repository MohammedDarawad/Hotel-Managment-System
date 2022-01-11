package com.example.hotelmanagmentsystem;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.model.RequestedService;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class ReceptionistManageRequests extends AppCompatActivity {
    private final Gson gson = new Gson();
    private final String apiURL = "http://10.0.2.2/get-requestedservices.php";
    private RecyclerView rvRequests;
    private RequestedService[] requestedServicesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_manage_requests);

        rvRequests = findViewById(R.id.rvRequests);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                requestedServicesList = new RequestedService[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        requestedServicesList[i] = gson.fromJson(response.getJSONObject(i).toString(), RequestedService.class);
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                rvRequests.setLayoutManager(new LinearLayoutManager(ReceptionistManageRequests.this));
                RequestedServicesAdapter adapter = new RequestedServicesAdapter(requestedServicesList, getApplicationContext());
                rvRequests.setAdapter(adapter);
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
