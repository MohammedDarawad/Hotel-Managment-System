package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;

import org.json.JSONException;
import org.json.JSONObject;

public class ReceptionistHomePage extends AppCompatActivity {
    private Intent intent;
    private TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionest_home_page);

        intent = getIntent();
        tvWelcome = findViewById(R.id.tvWelcome);

        getValues();
    }

    public void manageRequests(View view) {
        Intent intent = new Intent(this, ReceptionistManageRequests.class);
        startActivity(intent);
    }

    public void logout(View view) {
        finish();
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void changePassword(View view) {
        Intent intent2 = new Intent(this, ChangePassword.class);
        intent2.putExtra("emailAddress", intent.getStringExtra("emailAddress"));
        startActivity(intent2);
    }

    private void getValues() {
        String emailAddress = intent.getStringExtra("emailAddress");
        String apiURL = "http://10.0.2.2/get-receptionest-info-for-homepage.php?emailAddress=" + emailAddress;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, apiURL,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject obj = new JSONObject(response.toString());
                    tvWelcome.setText("Welcome " + obj.getString("firstName") + "  Id: " + obj.getString("uId"));
                } catch (JSONException e) {
                    e.printStackTrace();
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