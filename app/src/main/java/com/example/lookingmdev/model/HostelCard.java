package com.example.lookingmdev.model;

public class HostelCard {
    String name, address, image, shortDescription, characteristics;
    int price;
    double rating;

    public HostelCard(String name, String address, String image, String shortDescription, String characteristics, int price, double rating) {
        this.name = name;
        this.address = address;
        this.image = image;
        this.shortDescription = shortDescription;
        this.characteristics = characteristics;
        this.price = price;
        this.rating = rating;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getCharacteristics() {
        return characteristics;
    }

    public void setCharacteristics(String characteristics) {
        this.characteristics = characteristics;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }
}
