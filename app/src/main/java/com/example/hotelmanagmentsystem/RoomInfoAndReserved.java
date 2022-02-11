package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RoomInfoAndReserved extends AppCompatActivity {

    private EditText Room_Info ;
    private EditText first_Date ;
    private EditText Last_Date ;
    String uId = "" ;
    String URL = "" ;
    String date2 = "" ;
    String date1 = "" ;
    String rID = "" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentCom = getIntent();
        rID = intentCom.getStringExtra("rId");
        uId = intentCom.getStringExtra("uId");

        System.out.println(uId);
        date1 = intentCom.getStringExtra("date1");
        date2 = intentCom.getStringExtra("date2");
        URL = "http://10.0.2.2/get-room-info.php?rId="+rID;
        setContentView(R.layout.activity_room_info_and_reserved);
        Room_Info = findViewById(R.id.Room_Info);
        first_Date= findViewById(R.id.first_Date);
        Last_Date= findViewById(R.id.Last_Date);
        first_Date.setText(date1);
        Last_Date.setText(date2);

        setData();

    }

    private void setData() {
        String alldata = "";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String alldata = "";
                    JSONObject room = new JSONObject(response);
                    alldata += "The Room Id Is : "+String.valueOf(room.getInt("rId")) +"\n";
                    alldata += "The Room In Floor : "+String.valueOf(room.getInt("floor"))+"\n";
                    alldata += "The Room Type Is : "+String.valueOf(room.getString("type"))+"\n";
                    alldata += "The Capacity For Room Is : "+String.valueOf(room.getInt("capacity"))+"\n";
                    alldata += "The Price for Room Per One Day Is : "+String.valueOf(room.getDouble("price"))+"\n";

                    Room_Info.setText(alldata);

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

    public void reservedRoom(View view) {
        if(uId == null){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You are not login are you want to go to login ? ");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Intent intent = new Intent(RoomInfoAndReserved.this , Login.class);
                    startActivity(intent);
                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }else {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("You are sure to Reserved Room ?");
            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    addReserved();

                }
            });

            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            builder.show();
        }
    }

    private void addReserved() {
        String url = "http://10.0.2.2:80/ReservedRoom.php";
        RequestQueue queue = Volley.newRequestQueue(RoomInfoAndReserved.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(RoomInfoAndReserved.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(RoomInfoAndReserved.this,
                        "Fail Reserved Room = " + error, Toast.LENGTH_LONG).show();

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

                Map<String, String> params = new HashMap<String, String>();

                params.put("rId", rID);
                params.put("uId", uId);
                params.put("startDate", date1);
                params.put("endDate", date2);
                params.put("isCheckedIn", "0");

                return params;
            }
        };
        queue.add(request);
    }

    public void ShowImageRoom(View view) {
        Intent intent = new Intent(this , AllRoomImage.class);
        intent.putExtra("rId",rID);
        startActivity(intent);


    }
}