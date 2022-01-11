package com.example.hotelmanagmentsystem.packageRoomForManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.AlwanWork.Manegar;
import com.example.hotelmanagmentsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddRoom extends AppCompatActivity {
EditText edtFloorNumber;
EditText edtRoomType;
EditText edtRoomCapacity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_room);
        setupViews();
    }

    private void setupViews() {
        edtFloorNumber=findViewById(R.id.edtFloorNumber);
        edtRoomType=findViewById(R.id.edtRoomType);
        edtRoomCapacity=findViewById(R.id.edtRoomCapacity);
    }

  String FloorNumber;
  String RoomType;
  String RoomCapacity;
    public void btnOnClickAdd(View view) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(),0);
       FloorNumber = edtFloorNumber.getText().toString();
       RoomType = edtRoomType.getText().toString();
       RoomCapacity = edtRoomCapacity.getText().toString();
        if (validateInputs()) {
            addRoom(FloorNumber, RoomType, RoomCapacity);
        }
    }

    private void addRoom(String floorNumber, String roomType, String roomCapacity) {
        String url = "http://10.0.2.2:80/add_room.php";
        RequestQueue queue = Volley.newRequestQueue(AddRoom.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(AddRoom.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(AddRoom.this,
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
                params.put("floor", floorNumber);
                params.put("type", roomType);
                params.put("capacity", roomCapacity);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private boolean validateInputs() {
        if (FloorNumber.isEmpty()) {
            edtFloorNumber.setError("This field can not be blank");
            edtFloorNumber.requestFocus();
            return false;
        }
        else if (RoomType.isEmpty()) {
            edtRoomType.setError("This field can not be blank");
            edtRoomType.requestFocus();
            return false;
        }
        else if (RoomCapacity.isEmpty()) {
            edtRoomCapacity.setError("This field can not be blank");
            edtRoomCapacity.requestFocus();
            return false;
        }

        return true;
    }

    public void btnOnClickExit(View view) {
        Intent intent = new Intent(this, Manegar.class);
        startActivity(intent);
    }
}