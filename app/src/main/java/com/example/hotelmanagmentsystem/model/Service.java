package com.example.hotelmanagmentsystem.model;

import android.media.Image;

import java.util.List;

public class Service {
    private int sId;
    private String name;
    private String description;
    private float price;
    private String imageURL;
    private List<String> freeFor;
    private Image image;

    public Service(int sId, String name, String description, float price, String imageURL, List<String> freeFor) {
        this.sId = sId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageURL = imageURL;
        this.freeFor = freeFor;
    }

    public int getsId() {
        return sId;
    }

    public void setsId(int sId) {
        this.sId = sId;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
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

    public List<String> getFreeFor() {
        return freeFor;
    }

    public void setFreeFor(List<String> freeFor) {
        this.freeFor = freeFor;
    }
}
