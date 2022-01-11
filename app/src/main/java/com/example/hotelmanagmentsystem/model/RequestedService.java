package com.example.hotelmanagmentsystem.model;

public class RequestedService {
    private int id;
    private int sId;
    private int rId;

    public RequestedService(int id, int sId, int rId) {
        this.id = id;
        this.sId = sId;
        this.rId = rId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public int getrId() {
        return rId;
    }

    public void setrId(int rId) {
        this.rId = rId;
    }

    @Override
    public String toString() {
        return "RequestedService{" +
                "id=" + id +
                ", sId=" + sId +
                ", rId=" + rId +
                '}';
    }
}
