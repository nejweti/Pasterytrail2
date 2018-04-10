package com.nejat.pasterytrail2.Classes;

public class PastryLocInfo {
    String name;
    int tables;
    Double latitude;
    Double longitude;

    public PastryLocInfo(String name, int tables, Double latitude, Double longitude) {
        this.name = name;
        this.tables = tables;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public PastryLocInfo(){

    }


    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTables() {
        return tables;
    }

    public void setTables(int tables) {
        this.tables = tables;
    }
}
