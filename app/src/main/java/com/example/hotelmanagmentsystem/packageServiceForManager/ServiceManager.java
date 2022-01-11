package com.example.hotelmanagmentsystem.packageServiceForManager;


public class ServiceManager {
    private int sId;
    private String name;
    private String description;
    private float price;
    private String freeFor;
    private String imageURL;


    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getFreeFor() {
        return freeFor;
    }

    public void setFreeFor(String freeFor) {
        this.freeFor = freeFor;
    }
}
