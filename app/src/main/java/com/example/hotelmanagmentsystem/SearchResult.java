package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

public class SearchResult extends AppCompatActivity{

    Intent intentCom = getIntent();
    String date1 = intentCom.getStringExtra("date1");
    String date2 = intentCom.getStringExtra("date2");
    String url = "http://10.0.2.2/test/getAllRoom.php?firstdate=" + date1 + "&Seconddate=" + date2;
    List<Room> RoomList;
    RecyclerView recyclerView;
    SearchAdapter.RecyclerViewClickListener listener ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        recyclerView = findViewById(R.id.recyclerviewAllData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        RoomList = new ArrayList<>();

        getData();
    }

    public void getData() {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject room = array.getJSONObject(i);
                        RoomList.add(new Room(room.getDouble("price"),room.getInt("floor"),room.getInt("rId")));
                    }
                    setOnClickListener();
                    SearchAdapter adapter = new SearchAdapter(SearchResult.this,RoomList,listener);
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        Volley.newRequestQueue(this).add(stringRequest);

    }

    private void setOnClickListener() {
        listener = new SearchAdapter.RecyclerViewClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getApplicationContext() , RoomInfoAndReserved.class);
                intent.putExtra("rid",RoomList.get(position).getRid());
                startActivity(intent);
            }
        };
    }

}