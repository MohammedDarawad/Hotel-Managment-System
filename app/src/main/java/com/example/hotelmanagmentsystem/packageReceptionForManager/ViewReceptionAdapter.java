package com.example.hotelmanagmentsystem.packageReceptionForManager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewReceptionAdapter extends RecyclerView.Adapter<ViewReceptionAdapter.ViewHolder> {
    private ArrayList<String> captions1 = new ArrayList<>();
    private final Context context;
    TextView txtDeleted ;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    public static final String prefReceptionDelete = "prefReceptionDelete";
    private String st="";
    public ViewReceptionAdapter(ArrayList<String> captions1, Context context) {
        this.captions1 = captions1;
        this.context=context;

    }

    private void setupSharedPrefs() {
        prefs = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
        editor = prefs.edit();
    }
    private void getPref() {
        Gson gson = new Gson();
        String receptionDelete = prefs.getString(prefReceptionDelete,"");
        String st2 = gson.fromJson(receptionDelete,String.class);
       if(!receptionDelete.equals("")){
            st = st2;
        }
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_reception,
                parent,
                false);

        return new ViewHolder(v);
    }
    Button btnDelete;
    TextView txt1;
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
         txt1 = (TextView)cardView.findViewById(R.id.txtServiceInfo);
        txt1.setText(captions1.get(position));
        btnDelete = cardView.findViewById(R.id.btnEdit);
        String split[] = captions1.get(position).split("\n");
        String split1[] = split[1].split(":");
        String split2[]= split[0].split(" ");
        String id = split2[1];
        String name = split1[1];
        txtDeleted = cardView.findViewById(R.id.txtDrop);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 deleteReception(id,name);

            }
            });
    }

    @Override
    public int getItemCount() {
        return captions1.size();
    }
    private void deleteReception(String id,String name){
        String url = "http://10.0.2.2:80/delete-reception.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @SuppressLint("NewApi")
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responceJsonObject = new JSONObject(response);
                    if (responceJsonObject.has("error")) {
                        System.out.println(responceJsonObject.toString());
                        if (!responceJsonObject.getBoolean("error")) {
                            Toast.makeText(context.getApplicationContext(), responceJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            btnDelete.setVisibility(View.GONE);
                            txtDeleted.setVisibility(View.VISIBLE);
                            setupSharedPrefs();
                            getPref();
                            st += "\n You Delete Receptionist his Name is" +name+ " and his ID is" + id +" On "
                                    +java.time.LocalDate.now()+"\n" ;
                            Gson gson = new Gson();
                            String deleteReception = gson.toJson(st);
                            editor.putString(prefReceptionDelete,deleteReception);
                            editor.commit();
                        } else {
                            Toast.makeText(context.getApplicationContext(),
                                    "Some thing wrong happened", Toast.LENGTH_SHORT).show();
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
                params.put("uId", id );
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(request);

    }
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}