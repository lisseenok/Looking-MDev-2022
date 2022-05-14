package com.example.lookingmdev.model;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class HostelCard {
    String hostelName, city, address, image, shortDescription, fullDescription, id;
    int price;
    double rating;
    HashMap<String, Integer> listOfBookingDates;
    // лист для бронирования
    HashMap<String, Integer> listOfBookingDates2;
    int amountOfHostelRooms, currentAmountOfHostelRooms;
    int rooms, adults, children;

    public HostelCard(String hostelName, String city, String address, String image, String shortDescription, String fullDescription, int amountOfHostelRooms, int price, double rating, HashMap<String, Integer> listOfBookingDates, HashMap<String, Integer> listOfBookingDates2) {
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
        this.listOfBookingDates2 = listOfBookingDates2;
    }

    public HashMap<String, Integer> getListOfBookingDates2() {
        return listOfBookingDates2;
    }

    public void setListOfBookingDates2(HashMap<String, Integer> listOfBookingDates2) {
        this.listOfBookingDates2 = listOfBookingDates2;
    }

    public int getRooms() {
        return rooms;
    }

    public void setRooms(int rooms) {
        this.rooms = rooms;
    }

    public int getAdults() {
        return adults;
    }

    public void setAdults(int adults) {
        this.adults = adults;
    }

    public int getChildren() {
        return children;
    }

    public void setChildren(int children) {
        this.children = children;
    }

    public HostelCard() {
    }


    public void setCurrentAmountOfHostelRooms(int currentAmountOfHostelRooms) {
        this.currentAmountOfHostelRooms = currentAmountOfHostelRooms;
    }

    public int getCurrentAmountOfHostelRooms() {
        return currentAmountOfHostelRooms;
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

    public HashMap<String, Integer> getListOfBookingDates() {
        return listOfBookingDates;
    }

    public void setListOfBookingDates(HashMap<String, Integer> listOfBookingDates) {
        this.listOfBookingDates = listOfBookingDates;
    }

    public int getAmountOfHostelRooms() {
        return amountOfHostelRooms;
    }

    public void setAmountOfHostelRooms(int amountOfHostelRooms) {
        this.amountOfHostelRooms = amountOfHostelRooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @NonNull
    @Override
    public String toString() {
        return "HostelCard{" +
                "hostelName='" + hostelName + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                ", image='" + image + '\'' +
                ", shortDescription='" + shortDescription + '\'' +
                ", fullDescription='" + fullDescription + '\'' +
                ", id='" + id + '\'' +
                ", price=" + price +
                ", rating=" + rating +
                ", listOfBookingDates=" + listOfBookingDates +
                ", listOfBookingDates2=" + listOfBookingDates2 +
                ", amountOfHostelRooms=" + amountOfHostelRooms +
                ", currentAmountOfHostelRooms=" + currentAmountOfHostelRooms +
                ", rooms=" + rooms +
                ", adults=" + adults +
                ", children=" + children +
                '}';
    }
}



