package com.example.hotelmanagmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.example.hotelmanagmentsystem.model.Weather;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

public class DailyWeather extends ArrayAdapter<Weather> {

    private Context context;
    private List<Weather> WeatherList;

    public DailyWeather(@NonNull Context context, @NonNull List<Weather> WeatherList) {
        super(context, 0, WeatherList);
        this.context = context;
        this.WeatherList = WeatherList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.item_weather, parent, false);

        TextView DateTe = convertView.findViewById(R.id.DateTe);
        TextView TheTemp = convertView.findViewById(R.id.TheTemp);
        ImageView imWeather = convertView.findViewById(R.id.imWeather);

        Weather weather = WeatherList.get(position);

        TheTemp.setText(weather.getTemp() + " °C");
        try {
            Glide.with(context)
                    .load("https://openweathermap.org/img/w/" + weather.getIcon() + ".png")
                    .into(imWeather);
        } catch (Exception e) {

        }

        Date date = new Date(weather.getDate() * 1000);
        DateFormat dateFormat = new SimpleDateFormat("EEE , MMM yy", Locale.ENGLISH);
        dateFormat.setTimeZone(TimeZone.getTimeZone(weather.getTimetrue()));
        DateTe.setText(dateFormat.format(date));

        return convertView;
    }
}
