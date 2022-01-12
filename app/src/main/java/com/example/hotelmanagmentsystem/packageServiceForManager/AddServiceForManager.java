package com.example.hotelmanagmentsystem.packageServiceForManager;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.AlwanWork.Manegar;
import com.example.hotelmanagmentsystem.R;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AddServiceForManager extends AppCompatActivity {
private EditText txtName;
private  EditText txtDescription;
private EditText txtPrice;
private EditText txtFreeFor;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String prefService = "prefService";
    private String st="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service_for_manager);
        setupView();

        setupSharedPrefs();
        getPref();
    }

    private void getPref() {
        Gson gson = new Gson();
        String serviceAdd = prefs.getString(prefService,"");
        String st2 =  gson.fromJson(serviceAdd,String.class);
        if(!serviceAdd.equals("")){
            st = st2;
        }
    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        editor = prefs.edit();
    }


    private void setupView() {
        txtName=findViewById(R.id.TextServiceName);
        txtDescription=findViewById(R.id.TextDescription);
        txtPrice=findViewById(R.id.TextPrice);
        txtFreeFor=findViewById(R.id.TextFreeFor);
    }
    String name;
    String description;
    String price;
    String freeFor;
    public void btnOnClickAdd(View view) {
        InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        mgr.hideSoftInputFromWindow(view.getWindowToken(),0);
        name = txtName.getText().toString();
        description= txtDescription.getText().toString();
        price=txtPrice.getText().toString();
        freeFor=txtFreeFor.getText().toString();
        if (validateInputs()) {
            addService(name, description, price,freeFor);
            txtName.setText("");
            txtPrice.setText("");
            txtDescription.setText("");
            txtFreeFor.setText("");
        }

    }

    private void addService(String name, String description, String price, String freeFor) {
        String url = "http://10.0.2.2:80/add-service-for-manager.php";
        RequestQueue queue = Volley.newRequestQueue(AddServiceForManager.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(AddServiceForManager.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    String s;
                    if(freeFor.equals("")){
                        s = " and Its not free for any rooms";
                    }
                    else {
                        s=" and its Free For Rooms Types " + freeFor;
                    }
                    st += "\n You Add Service Name: " + name +" ,Description: "+
                            description + " ,Price: " + price + s +" On  "
                            + java.time.LocalDate.now()+"\n" ;
                    Gson gson = new Gson();
                    String addService = gson.toJson(st);
                    editor.putString(prefService,addService);
                    editor.commit();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(AddServiceForManager.this,
                        "Fail Sign Up = " + error, Toast.LENGTH_LONG).show();

            }
        }) {
            @Override
            public String getBodyContentType() {
                // as we are passing data in the form of url encoded
                // so we are passing the content type below
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }

            @Override
            protected Map<String, String> getParams() {

                // below line we are creating a map for storing
                // our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our
                // key and value pair to our parameters.
                params.put("name", name);
                params.put("description", description);
                params.put("price", price);
                params.put("freeFor",freeFor);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    private boolean validateInputs() {
        if (name.isEmpty()) {
            txtName.setError("This field can not be blank");
            txtName.requestFocus();
            return false;
        }
        else if (description.isEmpty()) {
            txtDescription.setError("This field can not be blank");
            txtDescription.requestFocus();
            return false;
        }
        else if (price.isEmpty()) {
            txtPrice.setError("This field can not be blank");
            txtPrice.requestFocus();
            return false;
        }

        return true;
    }
    public void btnOnClickExit(View view) {
        Intent intent = new Intent(this, Manegar.class);
        startActivity(intent);
    }
}