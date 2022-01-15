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
import com.example.hotelmanagmentsystem.model.RequestedService;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RequestedServicesAdapter extends RecyclerView.Adapter<RequestedServicesAdapter.ViewHolder> {

    private final String completeRequestApiURL = "http://10.0.2.2/complete-requested-service.php";
    private final List<RequestedService> requestedServicesList;
    private final Context context;
    private final Gson gson = new Gson();

    public RequestedServicesAdapter(List<RequestedService> requestedServicesList, Context context) {
        this.requestedServicesList = requestedServicesList;
        this.context = context;
    }

    @Override
    public RequestedServicesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_receptionestrequestedservices,
                parent,
                false);
        return new RequestedServicesAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;

        TextView tvRId = cardView.findViewById(R.id.tvRId);
        tvRId.setText(requestedServicesList.get(holder.getAdapterPosition()).getrId() + "");

        String nameApiURL = "http://10.0.2.2/get-service-name.php?sId=" + requestedServicesList.get(holder.getAdapterPosition()).getsId();
        TextView tvSId = cardView.findViewById(R.id.tvSId);
        StringRequest request = new StringRequest(Request.Method.GET, nameApiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responceJsonObject = new JSONObject(response);
                    if (responceJsonObject.has("name")) {
                        tvSId.setText(responceJsonObject.getString("name"));
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
        });
        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);

        Button btComplete = cardView.findViewById(R.id.btComplete);

        btComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleCompleteRequest(holder.getAdapterPosition(), requestedServicesList.get(holder.getAdapterPosition()).getId());
            }
        });
    }

    private void handleCompleteRequest(int position, int id) {
        StringRequest request = new StringRequest(Request.Method.POST, completeRequestApiURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responceJsonObject = new JSONObject(response);
                    if (responceJsonObject.has("hasError")) {
                        if (!responceJsonObject.getBoolean("hasError")) {
                            requestedServicesList.remove(position);
                            notifyItemRemoved(position);
                            notifyItemRangeChanged(position, getItemCount());
                            Toast.makeText(context, responceJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, responceJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
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
                params.put("id", id + "");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/x-www-form-urlencoded; charset=UTF-8";
            }
        };

        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public int getItemCount() {
        return requestedServicesList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}
