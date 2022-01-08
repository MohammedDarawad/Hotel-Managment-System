package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Sign_Up extends AppCompatActivity {
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtEmail;
    EditText edtPhoneNumber;
    EditText edtPassword;
TextView txtRes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        setupViews();
    }

    private void setupViews() {
        edtFirstName = findViewById(R.id.edtTextFirstName);
        edtLastName = findViewById(R.id.edtTextLastName);
        edtEmail = findViewById(R.id.edtTextEmail);
        edtPhoneNumber = findViewById(R.id.edtTextPhoneNumber);
        edtPassword = findViewById(R.id.edtTextPassword);
        txtRes=findViewById(R.id.txtResult);
    }

    public void btnSignUp(View view) {
        String FirstName = edtFirstName.getText().toString();
        String LastName = edtLastName.getText().toString();
        String Email = edtEmail.getText().toString();
        String PhoneNumber = edtPhoneNumber.getText().toString();
        String Password = edtPassword.getText().toString();
        addUsers(FirstName,LastName,Email,PhoneNumber,Password);
    }

    private void addUsers(String firstName, String lastName, String email, String phoneNumber, String password) {
        String url = "http://10.0.2.2:80/signup.php";
        RequestQueue queue = Volley.newRequestQueue(Sign_Up.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(Sign_Up.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    txtRes.setText(jsonObject.getString("message"));
                    txtRes.setVisibility(View.VISIBLE);
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Sign_Up.this,
                        "Fail Sign Up = " + error, Toast.LENGTH_LONG).show();
                txtRes.setText("Fail Sign Up = " + error);
                txtRes.setVisibility(View.VISIBLE);
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
                params.put("firstName", firstName);
                params.put("lastName", lastName);
                params.put("emailAddress", email);
                params.put("phoneNumber", phoneNumber);
                params.put("password", password);

                // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void btnOnClickContinue(View view) {
        Intent intent = new Intent(Sign_Up.this, HomePage.class);
        startActivity(intent);
    }
}