package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewReception extends AppCompatActivity {
    private RequestQueue queue;
    RecyclerView recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reception);
        recycler = findViewById(R.id.reception_recycler);
        queue = Volley.newRequestQueue(this);
        getData();


    }
    ArrayList<String> caption1 = new ArrayList<>();
    private void getData() {
        String url = "http://10.0.2.2:80/reception-info.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        caption1.add( "ID: " + obj.getString("uId") + "\n" +"Name: " + obj.getString("firstName")
                                + " " + obj.getString("lastName") + "\n" +
                                "Email: " + obj.getString("emailAddress") + "\n" +
                                "Phone Number: " + obj.getString("phoneNumber") );
                    }catch(JSONException exception){
                        Log.d("Error", exception.toString());
                    }
                }
                recycler.setLayoutManager(new LinearLayoutManager(ViewReception.this));
                ReceptionAdapter adapter = new ReceptionAdapter(caption1,getApplicationContext());
                recycler.setAdapter(adapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(ViewReception.this, error.toString(),
                        Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(request);
    }
}