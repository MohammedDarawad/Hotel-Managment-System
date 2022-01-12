package com.example.hotelmanagmentsystem.model;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.hotelmanagmentsystem.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CaptionedImagesAdapter
        extends RecyclerView.Adapter<CaptionedImagesAdapter.ViewHolder> {

    private final String apiURL = "http://10.0.2.2/request-service.php";
    private final Service[] servicesList;
    private final Context context;
    private final String rId;
    private Button btRequest;
    private TextView tvRequested;

    public CaptionedImagesAdapter(Service[] servicesList, Context context, String rId) {
        this.servicesList = servicesList;
        this.context = context;
        this.rId = rId;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_serviceslist,
                parent,
                false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = cardView.findViewById(R.id.ivRoomImage);
        Glide.with(context).load(servicesList[position].getImageURL()).into(imageView);
        TextView tvName = cardView.findViewById(R.id.tvSId);
        tvName.setText(servicesList[position].getName());
        tvRequested = cardView.findViewById(R.id.tvRequested);
        btRequest = cardView.findViewById(R.id.btComplete);
        btRequest.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                handleRequest(holder.getAdapterPosition() + 1, rId);
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicesList.length;
    }

    private void handleRequest(int sId, String rId) {
        StringRequest request = new StringRequest(Request.Method.POST, apiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responceJsonObject = new JSONObject(response);
                    if (responceJsonObject.has("error")) {
                        System.out.println(responceJsonObject.toString());
                        if (!responceJsonObject.getBoolean("error")) {
                            Toast.makeText(context.getApplicationContext(), responceJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("sId", sId + "");
                params.put("rId", rId);
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
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}