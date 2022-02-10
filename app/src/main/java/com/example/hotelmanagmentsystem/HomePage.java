package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.hotelmanagmentsystem.AlwanWork.Sign_Up;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.google.gson.Gson;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONArray;
import org.json.JSONException;

//Omar Tawafshah
public class HomePage extends AppCompatActivity {
    private DrawerLayout drawerLayout ;
    private String uId ;
    private TextView tloginAndReserved ;
    private ImageView imloginAndReserved ;
    private TextView tsignUpAndLogout ;
    private ImageView imsignUpAndLogout ;
    private SliderView sliderView;
    private String imageApiURL = "http://10.0.2.2/get-images.php?rId=";
    private ImageURLData[] imageURLs;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_home_page);
        drawerLayout = findViewById(R.id.drawer_layout);
        tloginAndReserved = findViewById(R.id.tloginAndReserved);
        imloginAndReserved = findViewById(R.id.imloginAndReserved);
        tsignUpAndLogout = findViewById(R.id.tsignUpAndLogout);
        imsignUpAndLogout = findViewById(R.id.imsignUpAndLogout);
        sliderView = findViewById(R.id.slSlider);
        gson = new Gson();
        Intent intent = getIntent();
        uId = intent.getStringExtra("uId");
        if(uId == null){
            tloginAndReserved.setText("Log in");
            imloginAndReserved.setImageResource(R.drawable.ic_login);
            tsignUpAndLogout.setText("Sign Up");
            imsignUpAndLogout.setImageResource(R.drawable.ic_create);

        }else {
            tloginAndReserved.setText("Reserved List ");
            imloginAndReserved.setImageResource(R.drawable.ic_login);
            tsignUpAndLogout.setText("Log Out");
            imsignUpAndLogout.setImageResource(R.drawable.ic_view_list);
        }
        getImageURLs();
    }

    private void getImageURLs() {
        imageApiURL += "0" + "&getAllImages=1";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, imageApiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                imageURLs = new ImageURLData[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        imageURLs[i] = gson.fromJson(response.getJSONObject(i).toString(), ImageURLData.class);
                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                populateSlider();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        }) {
        };
        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }

    private void populateSlider() {
        ReservedRoomMoreInfoAdapter adapter = new ReservedRoomMoreInfoAdapter(this, imageURLs);

        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);

        sliderView.setSliderAdapter(adapter);

        sliderView.setScrollTimeInSec(3);

        sliderView.setAutoCycle(true);

        sliderView.startAutoCycle();

    }

    public void clickmenu(View view) {
        openDrwawer(drawerLayout);
    }

    private void openDrwawer(DrawerLayout drawerLayout) {
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void clickLogo(View view) {
        closeDrwawer(drawerLayout);
    }

    private void closeDrwawer(DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void clickSearch(View view) {
        Intent intent = new Intent(this, Search.class);
        intent.putExtra("uId" , uId);
        startActivity(intent);
    }


    public void showWeatherclick(View view) {
        Intent intent = new Intent(this, WeatherActivite.class);
        startActivity(intent);
    }

    public void clickLogin(View view) {
        if(uId == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }else{
            Intent intent = new Intent(this, ReserveList.class);
            intent.putExtra("uId" , uId);
            startActivity(intent);
        }
    }

    public void clickSignUp(View view) {
        if(uId == null) {
            Intent intent = new Intent(this, Sign_Up.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, HomePage.class);
            startActivity(intent);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        closeDrwawer(drawerLayout);
    }
}