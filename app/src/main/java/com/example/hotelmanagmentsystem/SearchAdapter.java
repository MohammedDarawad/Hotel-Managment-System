package com.example.hotelmanagmentsystem;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.bumptech.glide.Glide;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private Context context;
    private List<Room> RoomList;
    Gson gson = new Gson();
    RecyclerViewClickListener listener;
    ImageURLData[] imageURLs;
    int rid;

    public SearchAdapter(Context context, List<Room> RoomList , RecyclerViewClickListener listener ) {
        this.context = context;
        this.RoomList = RoomList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.search_list, null);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Room room = RoomList.get(position);

        holder.textViewFloor.setText("The Room in Floor : "+String.valueOf(room.getFloor()));
        holder.textViewPrice.setText("Price Per One Night is : "+String.valueOf(room.getPrice()));
        holder.textViewRid.setText("The Room Id Is : "+String.valueOf(room.getRid()));
        rid = room.getRid();

        String url = "http://10.0.2.2/get-images.php?rId="+rid+"&getAllImages=0";


        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                imageURLs = new ImageURLData[response.length()];
                for (int i = 0; i < response.length(); i++) {
                    try {
                        imageURLs[i] = gson.fromJson(response.getJSONObject(i).toString(), ImageURLData.class);

                    } catch (JSONException exception) {
                        Log.d("Error", exception.toString());
                    }
                }
                Glide.with(context)
                        .load(imageURLs[0].getURL())
                        .into(holder.imageView);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error", error.toString());
            }
        }) {
        };
        RequestQueueSingleton.getInstance(context).addToRequestQueue(request);
    }

    @Override
    public int getItemCount() {
        return RoomList.size();
    }

    public interface RecyclerViewClickListener{
        void onClick(View view , int position);

    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView textViewFloor, textViewPrice , textViewRid;
        ImageView imageView;
        public SearchViewHolder(View itemView) {
            super(itemView);
            textViewFloor = itemView.findViewById(R.id.textViewFloor);
            textViewPrice = itemView.findViewById(R.id.textViewPrice);
            imageView = itemView.findViewById(R.id.imageView);
            textViewRid = itemView.findViewById(R.id.textViewRid);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            listener.onClick(view,getAdapterPosition());
        }
    }
}
