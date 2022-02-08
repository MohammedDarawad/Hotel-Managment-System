package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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

public class ReceptionistHomePage extends AppCompatActivity {
    private final Gson gson = new Gson();
    private final String apiURL = "http://10.0.2.2/get-requestedservices.php";
    private Intent intent;
    private TextView tvWelcome;
    private RecyclerView rvRequests;
    private TextView tvError;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionest_home_page);

        intent = getIntent();
        tvWelcome = findViewById(R.id.tvWelcome);
        Intent intent = getIntent();

        tvWelcome.setText("Welcome " + intent.getStringExtra("firstName"));

        rvRequests = findViewById(R.id.rvRequests);
        tvError = findViewById(R.id.tvError2);

        getData();
    }

    private void getData() {
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, apiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                List<RequestedService> requestedServicesList = new Gson().fromJson(response.toString(), new TypeToken<List<RequestedService>>() {
                }.getType());

                if (requestedServicesList.size() != 0) {
                    rvRequests.setLayoutManager(new LinearLayoutManager(ReceptionistHomePage.this));
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.receptionist_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.changePassword:
                changePassword();
                return true;
            case R.id.logout:
                logout();
                return true;
            case R.id.refreash:
                getData();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void manageRequests(View view) {
        Intent intent = new Intent(this, ReceptionistManageRequests.class);
        startActivity(intent);
    }

    public void logout() {
        finish();
        Intent intent = new Intent(ReceptionistHomePage.this, HomePage.class);
        startActivity(intent);
    }

    public void changePassword() {
        Intent intent2 = new Intent(ReceptionistHomePage.this, ChangePassword.class);
        intent2.putExtra("uId", intent.getStringExtra("uId"));
        startActivity(intent2);
    }
}