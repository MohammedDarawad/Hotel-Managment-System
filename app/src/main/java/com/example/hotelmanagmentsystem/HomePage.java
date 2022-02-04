package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hotelmanagmentsystem.AlwanWork.Sign_Up;
//Omar Tawafshah
public class HomePage extends AppCompatActivity {
    private DrawerLayout drawerLayout ;
    private String uId  = "";
    private TextView tloginAndReserved ;
    private ImageView imloginAndReserved ;
    private TextView tsignUpAndLogout ;
    private ImageView imsignUpAndLogout ;

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
        startActivity(intent);
    }


    public void showWeatherclick(View view) {
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void clickLogin(View view) {
        if(uId == null) {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(this, ReserveList.class);
            intent.putExtra("uId" , uId);
            startActivity(intent);
        }
    }

    public void clickSignUp(View view) {
        if(uId == null) {
            Intent intent = new Intent(this, Sign_Up.class);
            startActivity(intent);

        }else{
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