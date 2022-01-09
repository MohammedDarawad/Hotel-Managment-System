package com.example.hotelmanagmentsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
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

public class AddReception extends AppCompatActivity {
    EditText edtFirstName;
    EditText edtLastName;
    EditText edtEmail;
    EditText edtPhoneNumber;
    EditText edtPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_reception);
        setupViews();
    }

    private void setupViews() {
        edtFirstName = findViewById(R.id.edtTextFirstName);
        edtLastName = findViewById(R.id.edtTextLastName);
        edtEmail = findViewById(R.id.edtTextEmail);
        edtPhoneNumber = findViewById(R.id.edtTextPhoneNumber);
        edtPassword = findViewById(R.id.edtTextPassword);

    }

    String FirstName;
    String LastName;
    String Email;
    String PhoneNumber;
    String Password;
    public void btnAddReception(View view) {
        FirstName = edtFirstName.getText().toString();
        LastName = edtLastName.getText().toString();
        Email = edtEmail.getText().toString();
        PhoneNumber = edtPhoneNumber.getText().toString();
        Password = edtPassword.getText().toString();
        if (validateInputs()) {
            addReception(FirstName, LastName, Email, PhoneNumber, Password);

        }
    }

    private boolean validateInputs() {
        if (Email.isEmpty()) {
            edtEmail.setError("This field can not be blank");
            edtEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(Email).matches()) {
            edtEmail.setError("Enter valid email address");
            edtEmail.requestFocus();
            return false;
        } else if (Password.isEmpty()) {
            edtPassword.setError("This field can not be blank");
            edtPassword.requestFocus();
            return false;
        }
        else if (PhoneNumber.isEmpty()) {
            edtPhoneNumber.setError("This field can not be blank");
            edtPhoneNumber.requestFocus();
            return false;
        }
        else if (LastName.isEmpty()) {
            edtLastName.setError("This field can not be blank");
            edtLastName.requestFocus();
            return false;
        }
        else if (FirstName.isEmpty()) {
            edtFirstName.setError("This field can not be blank");
            edtFirstName.requestFocus();
            return false;
        }
        return true;
    }

    private void addReception(String firstName, String lastName, String email, String phoneNumber, String password) {
        String url = "http://10.0.2.2:80/add-reception.php";
        RequestQueue queue = Volley.newRequestQueue(AddReception.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(AddReception.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(AddReception.this,
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

    public void btnExit(View view) {
        Intent intent = new Intent(this, Manegar.class);
        startActivity(intent);
    }
}