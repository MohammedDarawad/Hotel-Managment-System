package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePassword extends AppCompatActivity {

    private final String apiURL = "http://10.0.2.2/change-password.php";
    private EditText etOld;
    private EditText etPassword;
    private EditText etPassword2;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        etOld = findViewById(R.id.etOld);
        etPassword = findViewById(R.id.etPassword);
        etPassword2 = findViewById(R.id.etPassword2);
        intent = getIntent();
    }

    public void change(View view) {
        if (validateInputs()) {
            handelChangePassword();
        }
    }

    private boolean validateInputs() {
        if (etOld.getText().toString().isEmpty()) {
            etOld.setError("This field can not be blank");
            etOld.requestFocus();
            return false;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.setError("Enter valid email address");
            etPassword.requestFocus();
            return false;
        } else if (etPassword2.getText().toString().isEmpty()) {
            etPassword2.setError("This field can not be blank");
            etPassword2.requestFocus();
            return false;
        } else if (etPassword.getText().toString().compareTo(etPassword2.getText().toString()) != 0) {
            etPassword2.setError("New Passwords Must Match");
            etPassword2.requestFocus();
            return false;
        }
        return true;
    }

    private void handelChangePassword() {
        StringRequest request = new StringRequest(Request.Method.POST, apiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responceJsonObject = new JSONObject(response);
                    if (responceJsonObject.has("hasError")) {
                        if (!responceJsonObject.getBoolean("hasError")) {
                            Toast.makeText(ChangePassword.this, responceJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(ChangePassword.this,
                                    responceJsonObject.getString("message"), Toast.LENGTH_LONG).show();
                        }
                        etPassword.setText("");
                        etPassword2.setText("");
                        etOld.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("emailAddress", intent.getStringExtra("emailAddress"));
                params.put("oldPassword", etOld.getText().toString());
                params.put("newPassword", etPassword.getText().toString());
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }
}