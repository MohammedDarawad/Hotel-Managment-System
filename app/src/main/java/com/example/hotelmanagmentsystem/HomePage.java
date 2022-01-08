package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class HomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    public void servicesList(View view) {
        Intent intent = new Intent(this, ServicesList.class);
        startActivity(intent);
    }

    public void login(View view) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void btnClickSignUp(View view) {
        Intent intent = new Intent(this, Sign_Up.class);
        startActivity(intent);
    }


    public void showReservedRoomsList(View view) {
        Intent intent = new Intent(this, ReserveList.class);
        startActivity(intent);
    }
}