package com.example.hotelmanagmentsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Toast;

public class Search extends AppCompatActivity {
    private CalendarView claendarview1 ;
    private CalendarView claendarview2 ;
    String date1 = "";
    String date2 = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        claendarview1 = findViewById(R.id.calendarView2);
        claendarview2 = findViewById(R.id.calendarView3);
        claendarview1.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date1 =i + "-" +(i1+1) +"-"+i2;
            }
        });

        claendarview2.setOnDateChangeListener(new CalendarView.OnDateChangeListener(){
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                date2 =i + "-" +(i1+1) +"-"+i2;
            }
        });

    }

    public void btnserach(View view) {
        if(date1 == "" && date2 == ""){
            Toast.makeText(getApplicationContext(), "Please Enter All Correct Date", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this,SearchResult.class);
            intent.putExtra("date1" , date1);
            intent.putExtra("date2" , date2);
            startActivity(intent);

        }
        
    }
}