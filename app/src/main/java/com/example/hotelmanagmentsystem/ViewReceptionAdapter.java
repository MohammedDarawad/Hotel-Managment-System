package com.example.hotelmanagmentsystem;

import android.content.Context;
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
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ViewReceptionAdapter extends RecyclerView.Adapter<ViewReceptionAdapter.ViewHolder> {
    private ArrayList<String> captions1 = new ArrayList<>();
    private final Context context;
    TextView txtDeleted ;
    public ViewReceptionAdapter(ArrayList<String> captions1, Context context) {
        this.captions1 = captions1;
        this.context=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_reception,
                parent,
                false);

        return new ViewHolder(v);
    }
    Button btnDelete;
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView txt1 = (TextView)cardView.findViewById(R.id.txtInfo1);
        txt1.setText(captions1.get(position));
        btnDelete = cardView.findViewById(R.id.btnDelete);
        String split[] = captions1.get(position).split("\n");
        String split2[]= split[0].split(" ");
        String id = split2[1];
        txtDeleted = cardView.findViewById(R.id.txtdeleted);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                 deleteReception(id);

            }
            });
    }

    @Override
    public int getItemCount() {
        return captions1.size();
    }
    private void deleteReception(String id){
        String url = "http://10.0.2.2:80/delete-reception.php";
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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