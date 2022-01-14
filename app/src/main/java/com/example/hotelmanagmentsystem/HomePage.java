package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.hotelmanagmentsystem.AlwanWork.Sign_Up;

public class HomePage extends AppCompatActivity {
    private DrawerLayout drawerLayout ;
    private String UserName ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        drawerLayout = findViewById(R.id.drawer_layout);
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
        Intent intent = new Intent(this, ServicesList.class);
        startActivity(intent);
    }


    public void clickservicesList(View view) {
        Intent intent = new Intent(this, ServicesList.class);
        startActivity(intent);
    }

    public void clickLogin(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void clickSignUp(View view) {
        Intent intent = new Intent(this, Sign_Up.class);
        startActivity(intent);
    }


    public void clickReservedList(View view) {
        Intent intent = new Intent(this, ReserveList.class);
        intent.putExtra("uId", "4");
        startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrwawer(drawerLayout);
    }
}