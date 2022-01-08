package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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


}