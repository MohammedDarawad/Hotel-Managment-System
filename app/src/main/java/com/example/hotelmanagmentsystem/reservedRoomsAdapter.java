package com.example.hotelmanagmentsystem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.model.ReservedRoom;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.text.SimpleDateFormat;

public class reservedRoomsAdapter
        extends RecyclerView.Adapter<reservedRoomsAdapter.ViewHolder> {

    private final String apiURL = "http://10.0.2.2/request-service.php";
    private final ReservedRoom[] reservedRoomsList;
    private final Context context;
    private final Gson gson = new Gson();
    private String imageApiURL = "";

    public reservedRoomsAdapter(ReservedRoom[] reservedRoomsList, Context context) {
        this.reservedRoomsList = reservedRoomsList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_reserved_rooms_list,
                parent,
                false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        String rId = reservedRoomsList[position].getrId() + "";
        ImageView imageView = cardView.findViewById(R.id.ivRoomImage);

        imageApiURL = "http://10.0.2.2/get-images.php?rId=" + rId + "&getAllImages=0";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, imageApiURL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                ImageURLData[] imageURL = new ImageURLData[response.length()];
                try {
                    imageURL[0] = gson.fromJson(response.getJSONObject(0).toString(), ImageURLData.class);
                    Glide.with(context).load(imageURL[0].getURL()).into(imageView);
                } catch (JSONException exception) {
                    Log.d("Error", exception.toString());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        }) {
        };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);

        TextView tvRoomNumber = cardView.findViewById(R.id.tvRoomNumber);
        tvRoomNumber.setText(rId);
        TextView tvReservedUntil = cardView.findViewById(R.id.tvReservedUntil);
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        tvReservedUntil.setText(formatter.format(reservedRoomsList[position].getendDate()));

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ReservedRoomMoreInfo.class);
                intent.putExtra("uId", reservedRoomsList[holder.getAdapterPosition()].getuId());
                intent.putExtra("rId", rId);
                intent.putExtra("startDate", formatter.format(reservedRoomsList[holder.getAdapterPosition()].getstartDate()));
                intent.putExtra("endDate", formatter.format(reservedRoomsList[holder.getAdapterPosition()].getendDate()));
                intent.putExtra("isCheckedIn", reservedRoomsList[holder.getAdapterPosition()].isCheckedIn());
                intent.putExtra("id", reservedRoomsList[holder.getAdapterPosition()].getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservedRoomsList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}