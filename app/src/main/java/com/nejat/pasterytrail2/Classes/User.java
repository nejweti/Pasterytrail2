package com.nejat.pasterytrail2.Classes;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

/**
 * Created by user on 3/29/2018.
 */

public class User extends Application {

    String CategoryId;
    String FoodId;
    String email;
    String password;
    int orderId;
    List<PastryLocation> nearbyPastery;
    Double Latitude;
    Double Longitude;

    public Double getLatitude() {
        return Latitude;
    }

    public void setLatitude(Double latitude) {
        Latitude = latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public void setLongitude(Double longitude) {
        Longitude = longitude;
    }

    public List<PastryLocation> getNearbyPastery() {
        return nearbyPastery;
    }

    public void setNearbyPastery(List<PastryLocation> nearbyPastery) {
        this.nearbyPastery = nearbyPastery;
    }

    public User(){}

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


    public String getFoodId() {
        return FoodId;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }

    public void setFoodId(String foodId) {
        FoodId = foodId;
    }

    public String getCategoryId() {
        return CategoryId;
    }

    public void setCategoryId(String categoryId) {
        CategoryId = categoryId;
    }


    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
