package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hotelmanagmentsystem.pacageViewRoom.ViewRoomForManager;

public class Manegar extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manegar);

    }

    public void onClickAddRoom(View view) {
        Intent intent = new Intent(this, AddRoom.class);
        startActivity(intent);
    }

    public void onClickViewRooms(View view) {
        Intent intent = new Intent(this, ViewRoomForManager.class);
        startActivity(intent);
    }

    public void onClickAddReseption(View view) {
        Intent intent = new Intent(this, AddReception.class);
        startActivity(intent);
    }

    public void onClickViewReseption(View view) {
        Intent intent = new Intent(this, ViewReception.class);
        startActivity(intent);
    }
}