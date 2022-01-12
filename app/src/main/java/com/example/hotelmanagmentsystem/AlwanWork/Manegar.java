package com.example.hotelmanagmentsystem.AlwanWork;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.packageReceptionForManager.AddReception;
import com.example.hotelmanagmentsystem.packageReceptionForManager.ViewReception;
import com.example.hotelmanagmentsystem.packageRoomForManager.AddRoom;
import com.example.hotelmanagmentsystem.packageRoomForManager.ViewRoomForManager;
import com.example.hotelmanagmentsystem.packageServiceForManager.AddServiceForManager;
import com.example.hotelmanagmentsystem.packageServiceForManager.ViewServiceForManager;

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

    public void btnOnClickAddService(View view) {
        Intent intent = new Intent(this, AddServiceForManager.class);
        startActivity(intent);
    }

    public void btnOnClickViewService(View view) {
        Intent intent = new Intent(this, ViewServiceForManager.class);
        startActivity(intent);
    }

    public void btnOnClickViewChanged(View view) {
        Intent intent = new Intent(this, ViewChanged.class);
        startActivity(intent);
    }
}