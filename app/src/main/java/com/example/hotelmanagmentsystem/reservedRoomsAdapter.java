package com.example.hotelmanagmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.hotelmanagmentsystem.model.ReservedRoom;

import java.text.SimpleDateFormat;

public class reservedRoomsAdapter
        extends RecyclerView.Adapter<reservedRoomsAdapter.ViewHolder> {

    private final String apiURL = "http://10.0.2.2/request-service.php";
    private final ReservedRoom[] reservedRoomsList;
    private final Context context;

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
        //TODO: fix the image link
        Glide.with(context).load("http://10.0.2.2/images/rooms/" + rId + "/1.jpg").into(imageView);
        TextView tvRoomNumber = cardView.findViewById(R.id.tvRoomNumber);
        tvRoomNumber.setText(rId);
        TextView tvReservedUntil = cardView.findViewById(R.id.tvReservedUntil);
        SimpleDateFormat formatter = new SimpleDateFormat(
                "dd/MM/yyyy");
        tvReservedUntil.setText(formatter.format(reservedRoomsList[position].getendDate()));
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("test click");
//                Intent intent = new Intent(this,RoomMoreInfo.class);
//                startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return reservedRoomsList.length;
    }

    private void handleRequest(int sId, int rId) {
//        StringRequest request = new StringRequest(Request.Method.POST, apiURL, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                try {
//                    JSONObject responceJsonObject = new JSONObject(response);
//                    if (responceJsonObject.has("error")) {
//                        System.out.println(responceJsonObject.toString());
//                        if (!responceJsonObject.getBoolean("error")) {
//                            Toast.makeText(context.getApplicationContext(), responceJsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                            btRequest.setVisibility(View.GONE);
//                            tvRequested.setVisibility(View.VISIBLE);
//                        } else {
//                            Toast.makeText(context.getApplicationContext(),
//                                    "Some thing wrong happened", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("Error", error.toString());
//            }
//        }) {
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("sId", sId + "");
//                params.put("rId", rId + "");
//                return params;
//            }
//
//            @Override
//            public String getBodyContentType() {
//                return "application/x-www-form-urlencoded; charset=UTF-8";
//            }
//        };
//
//        RequestQueueSingleton.getInstance(context.getApplicationContext()).addToRequestQueue(request);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;

        public ViewHolder(CardView cardView) {
            super(cardView);
            this.cardView = cardView;
        }
    }
}