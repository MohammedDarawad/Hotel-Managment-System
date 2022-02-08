package com.example.hotelmanagmentsystem.AlwanWork;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.packageRoomForManager.EditRoomForManager;
import com.example.hotelmanagmentsystem.packageRoomForManager.ViewRoomForManager;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Setting extends AppCompatActivity {
    private Intent intent;
    private String email;
    private String password;
    private TextView OldEmailAddress;
    private TextView OldPassword;
    private Button chEmail;
    private Button chPassword;
    private Button doneEmail;
    private Button donePassword;
    private EditText NewEmail;
    private EditText NewPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        intent = getIntent();
        email = intent.getStringExtra("email");
        password = intent.getStringExtra("pass");

        OldEmailAddress = findViewById(R.id.textViewEmail);
        OldEmailAddress.setText("Email:" + "\n" + email);

        OldPassword = findViewById(R.id.textViewPassword);
        OldPassword.setText("Password:" + "\n" + password);

        chEmail = findViewById(R.id.btnChangeEmail);
        chPassword = findViewById(R.id.btnChangePassword);

        doneEmail = findViewById(R.id.btnDoneEmail);
        donePassword = findViewById(R.id.btnDonePassword);

        NewEmail = findViewById(R.id.editTextNewEmail);
        NewPassword = findViewById(R.id.editTextNewPassword);
    }

    public void ChangeEmail(View view) {
        doneEmail.setVisibility(View.VISIBLE);
        NewEmail.setVisibility(View.VISIBLE);
        chEmail.setVisibility(View.INVISIBLE);
        NewEmail.setText("");
    }

    public void ChangePassword(View view) {
        donePassword.setVisibility(View.VISIBLE);
        NewPassword.setVisibility(View.VISIBLE);
        chPassword.setVisibility(View.INVISIBLE);
        NewPassword.setText("");
    }

    public void DoneEmail(View view) {
        if (validateInputs()) {
            editSetting(0);
            doneEmail.setVisibility(View.GONE);
            NewEmail.setVisibility(View.GONE);
            chEmail.setVisibility(View.VISIBLE);
        }
    }
    private boolean validateInputs() {
        if (NewEmail.getText().toString().isEmpty()) {
            NewEmail.setError("This field can not be blank");
            NewEmail.requestFocus();
            return false;
        }
        else if (!Patterns.EMAIL_ADDRESS.matcher(NewEmail.getText().toString()).matches()) {
            NewEmail.setError("Enter valid email address");
            NewEmail.requestFocus();
            return false;
        }
        return true;
    }

    private void editSetting(int check) {
        String url = "http://10.0.2.2:80/change_manager_info.php";
        RequestQueue queue = Volley.newRequestQueue(Setting.this);
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(String response) {
                Log.e("TAG", "RESPONSE IS " + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // on below line we are displaying a success toast message.
                    Toast.makeText(Setting.this,
                            jsonObject.getString("message"), Toast.LENGTH_LONG).show();
                    if (check == 0){
                        EmailAndPassword.info.setEmail(NewEmail.getText().toString());
                        OldEmailAddress.setText("Email:" + "\n" +NewEmail.getText().toString());
                    }
                    else if (check ==1){
                        EmailAndPassword.info.setPassword(NewPassword.getText().toString());
                        OldPassword.setText("Password:" + "\n" +NewPassword.getText().toString());
                    }

                } catch ( JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(Setting.this,
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
                params.put("check",check+"");
                params.put("email", NewEmail.getText().toString());
                params.put("newPassword", NewPassword.getText().toString());
                              // at last we are returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);
    }

    public void DonePassword(View view) {
        if (validateInputs2()) {
            editSetting(1);
            donePassword.setVisibility(View.GONE);
            NewPassword.setVisibility(View.GONE);
            chPassword.setVisibility(View.VISIBLE);
        }
    }

    private boolean validateInputs2() {
        if (NewPassword.getText().toString().isEmpty()) {
            NewPassword.setError("This field can not be blank");
            NewPassword.requestFocus();
            return false;
        }
        return true;
    }

    public void btnOnClickBack(View view) {
        Intent intent = new Intent(this, Manegar.class);
        startActivity(intent);
    }
}