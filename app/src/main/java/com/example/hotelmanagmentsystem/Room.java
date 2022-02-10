package com.example.hotelmanagmentsystem;

public class Room {
    private double price ;
    private int floor ;
    private int rid ;

    public Room() {

    }

    public Room(double price, int floor , int rid ) {
        this.price = price;
        this.floor = floor;
        this.rid = rid ;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }
}
