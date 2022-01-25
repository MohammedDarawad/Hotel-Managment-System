package com.example.hotelmanagmentsystem.AlwanWork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hotelmanagmentsystem.R;
import com.google.gson.Gson;

public class ViewChanged extends AppCompatActivity {
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private TextView txt;
    private TextView txt2;
    private TextView txt3;
    public static final String prefRoom = "prefRoom";
    public static final String prefRoomEdit = "prefRoomEdit";
    public static final String prefRoomDelete = "prefRoomDelete";
    public static final String prefReception = "prefReception";
    public static final String prefReceptionDelete = "prefReceptionDelete";
    public static final String prefService = "prefService";
    public static final String prefServiceEdit = "prefServiceEdit";
    public static final String prefServiceDelete = "prefServiceDelete";
    private Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_changed);
        txt=findViewById(R.id.txtChanged);
        txt2=findViewById(R.id.txtChangedRoom);
        txt3=findViewById(R.id.txtChangedService);
        spinner = findViewById(R.id.spinner);
       setupSharedPrefs();
       readData();
    }

    private void readData() {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                String receptionAdd="";
                String receptionDelete="";
                String roomAdd="";
                String editRoom="";
                String deleteRoom="";
                String serviceAdd="";
                String editService="";
                String deleteService="";

                Gson gson = new Gson();
                String st = prefs.getString(prefReception,"");
                if(!st.equals("")){
                    receptionAdd = gson.fromJson(st,String.class);
                }
                String st1 = prefs.getString(prefReceptionDelete,"");
                if (!st1.equals("")) {
                    receptionDelete = gson.fromJson(st1, String.class);
                }
                String st2 = prefs.getString(prefRoom,"");
                if (!st2.equals("")) {
                    roomAdd = gson.fromJson(st2, String.class);
                }
                String st3 = prefs.getString(prefRoomEdit,"");
                if (!st3.equals("")) {
                    editRoom = gson.fromJson(st3, String.class);
                }
                String st4 = prefs.getString(prefRoomDelete,"");
                if (!st4.equals("")) {
                    deleteRoom = gson.fromJson(st4, String.class);
                }
                String st5 = prefs.getString(prefService,"");
                if (!st5.equals("")) {
                    serviceAdd = gson.fromJson(st5, String.class);
                }
                String st6 = prefs.getString(prefServiceEdit,"");
                if (!st6.equals("")) {
                    editService = gson.fromJson(st6, String.class);
                }
                String st7 = prefs.getString(prefServiceDelete,"");
                if (!st7.equals("")) {
                    deleteService = gson.fromJson(st7, String.class);
                }
                String item = spinner.getSelectedItem().toString();
                if(item.equals("Changed For Receptionist")) {
                    if (st.equals("") && st1.equals("")) {
                        txt.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                    } else {
                        txt.setVisibility(View.VISIBLE);
                        txt2.setVisibility(View.GONE);
                        txt3.setVisibility(View.GONE);
                        txt.setText(" \n" + receptionAdd + "\n" + "=================================" + "\n" + receptionDelete);

                    }
                }
                else  if(item.equals("Changed For Rooms")) {
                    if (st2.equals("") && st3.equals("") && st4.equals("")) {
                        txt.setVisibility(View.GONE);
                        txt2.setVisibility(View.VISIBLE);
                        txt3.setVisibility(View.GONE);
                    } else {
                        txt.setVisibility(View.GONE);
                        txt2.setVisibility(View.VISIBLE);
                        txt3.setVisibility(View.GONE);
                        txt2.setText("\n" + roomAdd +
                                "\n" + "=================================" + "\n" + editRoom +
                                "\n" + "=================================" + "\n" + deleteRoom);
                    }
                }

                else if(item.equals("Changed For Services")) {

                    if (st5.equals("") && st6.equals("") && st7.equals("")) {
                        txt.setVisibility(View.GONE);
                        txt2.setVisibility(View.GONE);
                        txt3.setVisibility(View.VISIBLE);
                    } else {
                        txt.setVisibility(View.GONE);
                        txt2.setVisibility(View.GONE);
                        txt3.setVisibility(View.VISIBLE);
                        txt3.setText("\n"
                                + serviceAdd + "\n" + "=================================" + "\n" + editService +
                                "\n" + "=================================" + "\n" + deleteService);

                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }



        });
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }

    public void btnOnClickBack(View view) {
        Intent intent = new Intent(this, Manegar.class);
        startActivity(intent);
    }
}