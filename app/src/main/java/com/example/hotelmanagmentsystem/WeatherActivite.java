package com.example.hotelmanagmentsystem;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.model.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WeatherActivite extends AppCompatActivity {
    private TextView temp;
    private TextView city;
    private ImageView iconWeather;
    private Context context;
    private ListView lvDailyWeather;
    private String API_Key = "66f7ad629f1160c805a95816b7384929";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_activite);
        temp = findViewById(R.id.temp);
        city = findViewById(R.id.city);
        iconWeather = findViewById(R.id.iconWeather);
        lvDailyWeather = findViewById(R.id.lvDailyWeather);

        getTemp();
    }

    private void getTemp() {
        String URL = "https://api.openweathermap.org/data/2.5/weather?q=Ramallah&units=metric&appid=" + API_Key;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject object = response.getJSONObject("main");
                    String tempre = object.getString("temp");
                    temp.setText(tempre + " °C");


                    JSONObject Sys = response.getJSONObject("sys");
                    String country = Sys.getString("country");
                    city.setText("Ramallah , " + country);

                    JSONArray weather = response.getJSONArray("weather");
                    String icon = weather.getJSONObject(0).getString("icon");
                    loadImage(icon);


                } catch (JSONException | IOException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(request);

        setDatainList();
    }

    private void setDatainList() {
        String URL = "https://api.openweathermap.org/data/2.5/onecall?lat=31.895&lon=35.1846&exclude=hourly,minutely,current&units=metric&appid=" + API_Key;
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                List<Weather> weatherList = new ArrayList<>();
                try {
                    String timezone = response.getString("timezone");
                    JSONArray daily = response.getJSONArray("daily");
                    for (int i = 1; i < daily.length(); i++) {
                        Long date = daily.getJSONObject(i).getLong("dt");
                        Double temp = daily.getJSONObject(i).getJSONObject("temp").getDouble("day");
                        String icon = daily.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                        weatherList.add(new Weather(date, timezone, temp, icon));

                    }
                    DailyWeather dailyWeather = new DailyWeather(WeatherActivite.this, weatherList);
                    lvDailyWeather.setAdapter(dailyWeather);


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }

    private void loadImage(String icon) throws IOException {
        Glide.with(this)
                .load("https://openweathermap.org/img/w/" + icon + ".png")
                .into(iconWeather);
    }
}