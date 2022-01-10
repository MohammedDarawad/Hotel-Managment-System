package com.example.hotelmanagmentsystem.pacageViewRoom;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.example.hotelmanagmentsystem.R;
import com.example.hotelmanagmentsystem.ReservedRoomMoreInfo;
import com.example.hotelmanagmentsystem.ViewReceptionAdapter;
import com.example.hotelmanagmentsystem.model.CaptionedImagesAdapter;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.example.hotelmanagmentsystem.model.RequestQueueSingleton;
import com.example.hotelmanagmentsystem.model.ReservedRoom;
import com.example.hotelmanagmentsystem.model.Room;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class ViewRoomAdapter extends RecyclerView.Adapter<ViewReceptionAdapter.ViewHolder> {
    private ArrayList<String> captions1 = new ArrayList<>();
    private final Context context;
    private final Gson gson = new Gson();
    private final InitialRoomInfo[] RoomsList;
    private String imageApiURL = "http://10.0.2.2/get-images.php?rId=";
    private int i=1;
  //  private String rId;

    private Button btnMore;
    public ViewRoomAdapter(InitialRoomInfo[] RoomsList, Context context) {
        this.captions1 = captions1;
        this.context=context;
        this.RoomsList=RoomsList;
    }

    @Override
    public ViewReceptionAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CardView v = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_room_manager,
                parent,
                false);

        return new ViewReceptionAdapter.ViewHolder(v);
    }
    String rId;
    @Override
    public void onBindViewHolder(ViewReceptionAdapter.ViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        ImageView imageView = (ImageView) cardView.findViewById(R.id.firstRomeImage);
        TextView txt = (TextView)cardView.findViewById(R.id.txtFirstRoomInfoo);
        txt.setText("Room ID: " + RoomsList[position].getrId() + "\n"
                +"Capacity: " + RoomsList[position].getCapacity());
     //   String split[] = captions1.get(position).split("\n");
       // String split2[]= split[0].split(" ");
        //rId = split2[2];
        int pos = position;
       rId=RoomsList[position].getrId()+"";
        setImage(imageView);
        btnMore = cardView.findViewById(R.id.btnMoreInfo);
        btnMore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewRoomMoreInfoForManager.class);
                intent.putExtra("rId", RoomsList[pos].getrId());
                intent.putExtra("floor",RoomsList[pos].getFloor());
                intent.putExtra("isReserved",RoomsList[pos].isReserved());
                intent.putExtra("type",RoomsList[pos].getType());
                intent.putExtra("capacity", RoomsList[pos].getCapacity());
                v.getContext().startActivity(intent);

            }
        });
      /*  holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ViewRoomMoreInfoForManager.class);
                intent.putExtra("rId", rId);
                v.getContext().startActivity(intent);
            }
        });*/
    }
    private void setImage(ImageView imageView){
        String url = "http://10.0.2.2/get-images.php?rId=" + rId + "&getAllImages=0";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
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


    }
    @Override
    public int getItemCount() {
        return RoomsList.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }

    }
}