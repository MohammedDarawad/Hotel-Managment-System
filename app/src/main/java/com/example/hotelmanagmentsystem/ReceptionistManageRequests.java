package com.example.hotelmanagmentsystem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

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
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.List;

public class ReceptionistManageRequests extends AppCompatActivity {
    private final Gson gson = new Gson();
    private final String apiURL = "http://10.0.2.2/get-requestedservices.php";
    private RecyclerView rvRequests;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionist_manage_requests);

        rvRequests = findViewById(R.id.rvRequests);
        tvError = findViewById(R.id.tvError2);

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<RequestedService> requestedServicesList = new Gson().fromJson(response.toString(), new TypeToken<List<RequestedService>>() {
                }.getType());

                if (requestedServicesList.size() != 0) {
                    rvRequests.setLayoutManager(new LinearLayoutManager(ReceptionistManageRequests.this));
                    RequestedServicesAdapter adapter = new RequestedServicesAdapter(requestedServicesList, getApplicationContext());
                    rvRequests.setAdapter(adapter);
                } else {
                    rvRequests.setVisibility(View.GONE);
                    tvError.setVisibility(View.VISIBLE);
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
