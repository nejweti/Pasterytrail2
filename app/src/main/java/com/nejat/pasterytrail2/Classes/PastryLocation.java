package com.nejat.pasterytrail2.Classes;

public class PastryLocation {
    String name;
    int tables;

    public PastryLocation(){}

    public PastryLocation(String name, int tables) {
        this.name = name;
        this.tables = tables;
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
