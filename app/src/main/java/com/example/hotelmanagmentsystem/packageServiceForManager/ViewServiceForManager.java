package com.example.hotelmanagmentsystem.packageServiceForManager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.AlwanWork.Manegar;
import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

public class ViewServiceForManager extends AppCompatActivity {
    private ServiceManager[] ServiceList;
    private RequestQueue queue;
    private String apiURL = "http://10.0.2.2:80/service-info.php";
    private Gson gson;
    private RecyclerView recycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_service_for_manager);
        recycler = findViewById(R.id.service_recycler);
        queue = Volley.newRequestQueue(this);
        gson = new Gson();
        try {


            RequestQueue queue = Volley.newRequestQueue(ViewServiceForManager.this);

            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiURL,
                    null, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    ServiceList = new ServiceManager[response.length()];
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            ServiceList[i] = gson.fromJson(response.getJSONObject(i).toString(), ServiceManager.class);
                        } catch (JSONException exception) {
                            Log.d("Error", exception.toString());
                        }

                    }
                    recycler.setLayoutManager(new LinearLayoutManager(ViewServiceForManager.this));
                    ViewServiceAdapter adapter = new ViewServiceAdapter(ServiceList, getApplicationContext());
                    recycler.setAdapter(adapter);

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.d("Error", error.toString());
                }
            });

            RequestQueueSingleton.getInstance(this).addToRequestQueue(request);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void btnOnClickBack(View view) {
        Intent intent = new Intent(this, Manegar.class);
        startActivity(intent);
    }
}

