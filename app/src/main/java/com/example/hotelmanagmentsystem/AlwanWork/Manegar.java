package com.example.hotelmanagmentsystem.AlwanWork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.hotelmanagmentsystem.HomePage;
import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.packageReceptionForManager.AddReception;
import com.example.hotelmanagmentsystem.packageReceptionForManager.ViewReception;
import com.example.hotelmanagmentsystem.packageRoomForManager.AddRoom;
import com.example.hotelmanagmentsystem.packageRoomForManager.ViewRoomForManager;
import com.example.hotelmanagmentsystem.packageServiceForManager.AddServiceForManager;
import com.example.hotelmanagmentsystem.packageServiceForManager.ViewServiceForManager;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;

public class Manegar extends AppCompatActivity {
     private NavigationView nav;
     private DrawerLayout draw;
     private Menu menu;
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
    private Intent intent;
    private String email;
    private String password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_manegar);
        nav = findViewById(R.id.nav_menu);
        nav.setItemIconTintList(null);
        draw = findViewById(R.id.D_Layout);
        menu = nav.getMenu();
        getActions();
        txt=findViewById(R.id.txtChanged);
        txt2=findViewById(R.id.txtChangedRoom);
        txt3=findViewById(R.id.txtChangedService);
        spinner = findViewById(R.id.spinner);
        setupSharedPrefs();
        readData();
        intent = getIntent();
        email = intent.getStringExtra("emailAddress");
        password = intent.getStringExtra("password");
        if (email != null && password != null) {
            EmailAndPassword.info.setEmail(email);
            EmailAndPassword.info.setPassword(password);
        }
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
                        txt.setText(" \n" + receptionAdd + "\n" + "================================" +
                                "\n" + receptionDelete + "\n"+ "\n");

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
                                "\n" + "================================" + "\n" + editRoom +
                                "\n" + "================================" + "\n" + deleteRoom + "\n"+ "\n");
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
                                + serviceAdd + "\n" + "================================" + "\n" + editService +
                                "\n" + "================================" + "\n" + deleteService + "\n"+ "\n");

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

    private void getActions() {
        nav.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                checkItemSelected(item);
                return false;
            }
        });
    }
  private void checkItemSelected(MenuItem item){
        switch (item.getItemId()){
            case (R.id.manager_addRoom):
                onClickAddRoom();
                break;
            case (R.id.manager_viewRoom):
                onClickViewRooms();
                break;
            case (R.id.manager_addReceptionist):
                onClickAddReseption();
                break;
            case (R.id.manager_viewReceptionist):
                onClickViewReseption();
                break;
            case (R.id.manager_addService):
                btnOnClickAddService();
                break;
            case (R.id.manager_viewService):
                btnOnClickViewService();
                break;
            case (R.id.manager_logOut):
                btnOnClickBack();
                break;
            case (R.id.manager_setting):
                Intent intent = new Intent(this, Setting.class);
                intent.putExtra("email", EmailAndPassword.info.getEmail());
                intent.putExtra("pass", EmailAndPassword.info.getPassword());
                startActivity(intent);
                break;
        }
  }
    public void onClickAddRoom() {
        Intent intent = new Intent(this, AddRoom.class);
        startActivity(intent);
    }

    public void onClickViewRooms() {
        Intent intent = new Intent(this, ViewRoomForManager.class);
        startActivity(intent);
    }

    public void onClickAddReseption() {
        Intent intent = new Intent(this, AddReception.class);
        startActivity(intent);
    }

    public void onClickViewReseption() {
        Intent intent = new Intent(this, ViewReception.class);
        startActivity(intent);
    }

    public void btnOnClickAddService() {
        Intent intent = new Intent(this, AddServiceForManager.class);
        startActivity(intent);
    }

    public void btnOnClickViewService() {
        Intent intent = new Intent(this, ViewServiceForManager.class);
        startActivity(intent);
    }


    public void btnOnClickBack() {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void onclickmenu(View view) {
        draw.openDrawer(GravityCompat.START);
    }
}