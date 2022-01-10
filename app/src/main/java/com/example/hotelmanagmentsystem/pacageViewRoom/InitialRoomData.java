package com.example.hotelmanagmentsystem.pacageViewRoom;

public class InitialRoomData {
    private String imageRoomURL;
    private int rid;
    private int capacity;

    public InitialRoomData(String imageRoomURL, int rid, int capacity) {
        this.imageRoomURL = imageRoomURL;
        this.rid = rid;
        this.capacity = capacity;
    }

    public String getImageRoomURL() {
        return imageRoomURL;
    }

    public void setImageRoomURL(String imageRoomURL) {
        this.imageRoomURL = imageRoomURL;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }
}
