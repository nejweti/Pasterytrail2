package com.nejat.pasterytrail2.Classes;

/**
 * Created by user on 3/20/2018.
 */

public class NavData {
    int icon_id;
    String name;

    public NavData(int icon_id, String name) {
        this.icon_id = icon_id;
        this.name = name;
    }

    public int getIcon_id() {
        return icon_id;
    }

    public void setIcon_id(int icon_id) {
        this.icon_id = icon_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
