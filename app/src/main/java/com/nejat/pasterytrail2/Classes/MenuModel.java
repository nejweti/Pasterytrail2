package com.nejat.pasterytrail2.Classes;

/**
 * Created by user on 3/25/2018.
 */

public class MenuModel {
    String id;
    String image;
    String name;

    public MenuModel() {

    }

    public MenuModel(String id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
