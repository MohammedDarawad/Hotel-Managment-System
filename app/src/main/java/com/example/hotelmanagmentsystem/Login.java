package com.example.hotelmanagmentsystem;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {
    private final String apiURL = "http://10.0.2.2/login.php";
    private RequestQueue queue;
    private String emailAddress;
    private String password;
    private EditText etEmail;
    private EditText etPassword;
    private CheckBox cbRemeberLogin;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Gson gson;
    private JSONObject responceJsonObject;
    private String errorMessage_Login = "No Such User Exists";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        cbRemeberLogin = findViewById(R.id.cbRememberLogin);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        gson = new Gson();
    }

    public void login(View view) throws JSONException {
        emailAddress = etEmail.getText().toString();
        password = etPassword.getText().toString();

        if (validateInputs()) {
            validateLogin();
        }
    }

    public void signup(View view) {
        Intent intent = new Intent(this, Sign_Up.class);
        startActivity(intent);
    }

    private boolean validateInputs() {
        if (emailAddress.isEmpty()) {
            etEmail.setError("This field can not be blank");
            etEmail.requestFocus();
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
            etEmail.setError("Enter valid email address");
            etEmail.requestFocus();
            return false;
        } else if (password.isEmpty()) {
            etPassword.setError("This field can not be blank");
            etPassword.requestFocus();
            return false;
        }
        return true;
    }

    private void validateLogin() {
        StringRequest request = new StringRequest(Request.Method.POST, apiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    responceJsonObject = new JSONObject(response);
                    if (responceJsonObject.has("user")) {
                        if (!responceJsonObject.isNull("user")) {
                            loginIsValid();
                        } else {
                            etPassword.setText("");
                            Toast.makeText(Login.this,
                                    errorMessage_Login, Toast.LENGTH_LONG).show();
                        }
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
                params.put("emailAddress", emailAddress);
                params.put("password", password);
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueueSingleton.getInstance(this).addToRequestQueue(request);
    }

    private void loginIsValid() throws JSONException {
        if (cbRemeberLogin.isChecked()) {
            editor.putString("emailAddress", "emailAddress");
            editor.commit();
        }
        Intent intent = null;
        int userType = responceJsonObject.getJSONObject("user").getInt("userType");
        if (userType == 0) {
            intent = new Intent(Login.this, Manegar.class);
        } else if (userType == 1) {
            intent = new Intent(Login.this, HomePage.class);
        } else if (userType == 2) {
            intent = new Intent(Login.this, HomePage.class);
        }
        intent.putExtra("emailAddress", emailAddress);
        startActivity(intent);
    }
}