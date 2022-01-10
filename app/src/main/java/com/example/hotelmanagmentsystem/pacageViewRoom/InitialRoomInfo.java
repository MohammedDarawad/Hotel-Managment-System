package com.example.hotelmanagmentsystem.pacageViewRoom;

public class InitialRoomInfo {
    private int rId;
    private int floor;
    private int capacity;
    private String type;
    private int isReserved;

    public InitialRoomInfo(int rId, int floor, int capacity, String type, int isReserved) {
        this.rId = rId;
        this.floor = floor;
        this.capacity = capacity;
        this.type = type;
        this.isReserved = isReserved;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int isReserved() {
        return isReserved;
    }

    public void setReserved(int reserved) {
        isReserved = reserved;
    }

    @Override
    public String toString() {
        return "Room{" +
                "rId=" + rId +
                ", floor=" + floor +
                ", capacity=" + capacity +
                ", type='" + type + '\'' +
                ", isReserved=" + isReserved +
                '}';
    }
}
