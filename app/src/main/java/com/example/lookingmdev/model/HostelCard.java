package com.example.lookingmdev.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HostelCard {
    String hostelName, city, address, image, shortDescription, fullDescription;
    int price;
    double rating;
    HashMap<Date, Integer> listOfBookingDates;
    int amountOfHostelRooms;

    public HostelCard() {

    }

    public HostelCard(String hostelName, String city, String address, String image, String shortDescription, String fullDescription, int amountOfHostelRooms, int price, double rating, HashMap<Date, Integer> listOfBookingDates) {
        this.hostelName = hostelName;
        this.city = city;
        this.address = address;
        this.image = image;
        this.shortDescription = shortDescription;
        this.fullDescription = fullDescription;
        this.amountOfHostelRooms = amountOfHostelRooms;
        this.price = price;
        this.rating = rating;
        this.listOfBookingDates = listOfBookingDates;
    }

    public String getHostelName() {
        return hostelName;
    }

    public void setHostelName(String hostelName) {
        this.hostelName = hostelName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getFullDescription() {
        return fullDescription;
    }

    public void setFullDescription(String fullDescription) {
        this.fullDescription = fullDescription;
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

    public void setRating(double rating) {
        this.rating = rating;
    }

    public HashMap<Date, Integer> getListOfBookingDates() {
        return listOfBookingDates;
    }

    public void setListOfBookingDates(HashMap<Date, Integer> listOfBookingDates) {
        this.listOfBookingDates = listOfBookingDates;
    }

    public int getAmountOfHostelRooms() {
        return amountOfHostelRooms;
    }

    public void setAmountOfHostelRooms(int amountOfHostelRooms) {
        this.amountOfHostelRooms = amountOfHostelRooms;
    }
}

