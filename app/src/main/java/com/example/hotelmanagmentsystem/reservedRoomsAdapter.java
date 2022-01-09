package com.example.hotelmanagmentsystem;

import android.content.Context;
import android.content.Intent;
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
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ReservedRoomMoreInfo.class);
                intent.putExtra("rId", rId);
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