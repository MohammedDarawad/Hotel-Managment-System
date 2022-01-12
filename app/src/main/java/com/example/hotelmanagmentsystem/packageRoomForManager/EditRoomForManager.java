package com.example.hotelmanagmentsystem.packageRoomForManager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditRoomForManager extends AppCompatActivity {
    private Intent intent;
    private int rId;
    private String type;
    private int capacity;
    private EditText txtEditType;
    private EditText txtEditCapacity;
    private int check;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String prefRoomEdit = "prefRoomEdit";
    public static final String prefRoomDelete = "prefRoomDelete";
    private String st="";
    private String st1="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_room_for_manager);
        intent = getIntent();
        rId =intent.getIntExtra("rId",0);
        type=intent.getStringExtra("type");
        capacity=intent.getIntExtra("capacity",0);
        txtEditType = findViewById(R.id.txtEditType);
        txtEditType.setHint("Old Type is "+type);
        txtEditCapacity = findViewById(R.id.txtEditCapacity);
        txtEditCapacity.setHint("Old Capacity is "+capacity);
        setupSharedPrefs();
        getPref();
    }

    private void getPref() {
        Gson gson = new Gson();
        String roomEdit = prefs.getString(prefRoomEdit,"");
        String st2 =  gson.fromJson(roomEdit,String.class);
        if(!roomEdit.equals("")){
            st = st2;
        }
        String roomDelete = prefs.getString(prefRoomDelete,"");
        String st3 =  gson.fromJson(roomDelete,String.class);
        if(!roomDelete.equals("")){
            st1 = st3;
        }
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }
    public void btnOnClickEdit(View view) {
        if (!txtEditType.getText().toString().equals("") && txtEditCapacity.getText().toString().equals("")){
            check = 0;
            editRoom(check);
        }
        else if(txtEditType.getText().toString().equals("") && !txtEditCapacity.getText().toString().equals("")){
            check = 1;
            editRoom(check);
        }
        else if(!txtEditType.getText().toString().equals("") && !txtEditCapacity.getText().toString().equals("")){
            check = 2;
            editRoom(check);
        }
    }
    private void editRoom(int check) {
        String url = "http://10.0.2.2:80/update-room.php";
        RequestQueue queue = Volley.newRequestQueue(EditRoomForManager.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(EditRoomForManager.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (check == 0){
                    st += "\n You Edit Room Number " + rId +" ,Set Type: "+
                            txtEditType.getText().toString() +" On "
                            +java.time.LocalDate.now()+"\n" ;
                    }
                    else if (check == 1){
                        st += "\n You Edit Room Number " + rId +" ,Set Capacity: "+
                                txtEditCapacity.getText().toString() +" On "
                                +java.time.LocalDate.now()+"\n" ;
                    }
                    else {
                        st += "\n You Edit Room Number " + rId +" ,Set Type: "+
                                txtEditType.getText().toString() + "and Set Capacity: "+
                                txtEditCapacity.getText().toString() +" On "
                                +java.time.LocalDate.now()+"\n" ;
                    }
                    Gson gson = new Gson();
                    String editRoom = gson.toJson(st);
                    editor.putString(prefRoomEdit,editRoom);
                    editor.commit();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(EditRoomForManager.this,
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
               params.put("check",check+"");
                params.put("rId", rId+"");
                params.put("type", txtEditType.getText().toString());
                params.put("capacity", txtEditCapacity.getText().toString());

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
        Intent intent = new Intent(this, ViewRoomForManager.class);
        startActivity(intent);
    }

    public void btnOnClickDelete(View view) {
        String url = "http://10.0.2.2:80/delete-room.php";
        RequestQueue queue = Volley.newRequestQueue(EditRoomForManager.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(EditRoomForManager.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    st1 += "\n You Deleted Room Number " + rId +" On "
                            +java.time.LocalDate.now()+"\n" ;
                Gson gson = new Gson();
                String deleteRoom = gson.toJson(st1);
                editor.putString(prefRoomDelete,deleteRoom);
                editor.commit();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(EditRoomForManager.this,
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

                params.put("rId", rId+"");

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
        Intent intent = new Intent(this, ViewRoomForManager.class);
        startActivity(intent);
    }
}