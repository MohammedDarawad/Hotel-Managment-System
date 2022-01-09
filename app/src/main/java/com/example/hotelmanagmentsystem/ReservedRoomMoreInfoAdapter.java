package com.example.hotelmanagmentsystem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.hotelmanagmentsystem.model.ImageURLData;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class ReservedRoomMoreInfoAdapter extends SliderViewAdapter<ReservedRoomMoreInfoAdapter.SliderAdapterViewHolder> {

    private final ImageURLData[] imageURLs;

    public ReservedRoomMoreInfoAdapter(Context context, ImageURLData[] imageURLs) {
        this.imageURLs = imageURLs;
    }

    @Override
    public SliderAdapterViewHolder onCreateViewHolder(ViewGroup parent) {
        View inflate = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_reservedroommoreinfo, null);
        return new SliderAdapterViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(SliderAdapterViewHolder viewHolder, final int position) {

        Glide.with(viewHolder.itemView)
                .load(imageURLs[position].getURL())
                .fitCenter()
                .into(viewHolder.imageViewBackground);
    }

    @Override
    public int getCount() {
        return imageURLs.length;
    }

    static class SliderAdapterViewHolder extends SliderViewAdapter.ViewHolder {
        View itemView;
        ImageView imageViewBackground;

        public SliderAdapterViewHolder(View itemView) {
            super(itemView);
            imageViewBackground = itemView.findViewById(R.id.ivImage);
            this.itemView = itemView;
        }
    }
}