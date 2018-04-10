package com.nejat.pasterytrail2.Classes;

/**
 * Created by user on 3/29/2018.
 */

public class Food {
    String category;
    String description;
    String image;
    String name;
    String price;
    int discount;

    public Food(){}

    public Food(String category, String description, String image, String name, String price,int discount) {
        this.category = category;
        this.description = description;
        this.image = image;
        this.name = name;
        this.price = price;
        this.discount = discount;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
