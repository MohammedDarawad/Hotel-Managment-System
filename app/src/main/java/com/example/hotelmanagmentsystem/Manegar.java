package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class Manegar extends AppCompatActivity {
TextView txtChangeRooms;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manegar);
        txtChangeRooms=findViewById(R.id.txtChangeInfo);
    }

    public void onClickAddRoom(View view) {
        Intent intent = new Intent(this, AddRoom.class);
        startActivity(intent);
    }

    public void onClickViewRooms(View view) {
    }

    public void onClickAddReseption(View view) {
        Intent intent = new Intent(this, AddReception.class);
        startActivity(intent);
    }
}