package com.example.hotelmanagmentsystem.packageServiceForManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.packageRoomForManager.EditRoomForManager;
import com.example.hotelmanagmentsystem.packageRoomForManager.ViewRoomForManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditServiceForManager extends AppCompatActivity {
private EditText txtName;
private EditText txtDescription;
private  EditText txtPrice;
private EditText txtFreeFor;
private Intent intent;
private int sId;
private String name;
private String description;
private int price;
private String freeFor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_service_for_manager);
        intent = getIntent();
        sId =intent.getIntExtra("sId",0);
        name =intent.getStringExtra("name");
        description =intent.getStringExtra("description");
        price =intent.getIntExtra("price",0);
        freeFor =intent.getStringExtra("freeFor");
        setUpView();
    }

    private void setUpView() {
        txtName = findViewById(R.id.TextServiceName);
        txtDescription = findViewById(R.id.TextDescription);
        txtPrice = findViewById(R.id.TextPrice);
        txtFreeFor = findViewById(R.id.TextFreeFor);
        txtName.setHint("Old Name is "+name);
        txtDescription.setHint("Old Description is " +description);
        txtPrice.setHint("Old Price is "+price);
        if(!freeFor.equals("")) {
            txtFreeFor.setHint("Old Free For " + freeFor + "\n" + "To Clear Free For Data Just Click Space");
        }
        else {
            txtFreeFor.setHint("Old Free For: Not Free For Any Rooms");
            txtFreeFor.setHeight(100);
        }
        }

    public void btnOnClickEdit(View view) {
        String url = "http://10.0.2.2:80/update-service-for-manager.php";
        RequestQueue queue = Volley.newRequestQueue(EditServiceForManager.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(EditServiceForManager.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(EditServiceForManager.this,
                        "Fail Sign Up = " + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("sId",sId+"");
                params.put("name", txtName.getText().toString());
                params.put("description", txtDescription.getText().toString());
                params.put("price", txtPrice.getText().toString());
                params.put("freeFor", txtFreeFor.getText().toString());

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
        Intent intent = new Intent(this, ViewServiceForManager.class);
        startActivity(intent);
    }
}