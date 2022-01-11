package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ReceptionistHomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionest_home_page);
    }

    public void manageRequests(View view) {
        Intent intent = new Intent(this, ReceptionistManageRequests.class);
        startActivity(intent);
    }

    public void logout(View view) {
        finish();
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }

    public void changePassword(View view) {
    }

    public void search(View view) {
    }
}