package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class ReceptionistHomePage extends AppCompatActivity {
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receptionest_home_page);

        intent = getIntent();
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
        Intent intent2 = new Intent(this, ChangePassword.class);
        intent2.putExtra("emailAddress", intent.getStringExtra("emailAddress"));
        startActivity(intent2);
    }
}