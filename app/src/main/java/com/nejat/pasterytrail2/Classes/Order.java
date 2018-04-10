package com.nejat.pasterytrail2.Classes;

/**
 * Created by user on 3/31/2018.
 */

public class Order {
    int id;
    private String product_id;
    private String product_name;
    private int product_quantity ;
    private String product_price;
    private int product_discount;

    public Order(int id,String product_id, String product_name, String product_price, int product_quantity,int product_discount) {
        this.id = id;
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
    }

    public Order(String product_id, String product_name, String product_price, int product_quantity,int product_discount) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_quantity = product_quantity;
        this.product_price = product_price;
        this.product_discount = product_discount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_id() {
        return product_id;
    }

    public void setProduct_id(String product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public int getProduct_quantity() {
        return product_quantity;
    }

    public void setProduct_quantity(int product_quantity) {
        this.product_quantity = product_quantity;
    }

    public String getProduct_price() {
        return product_price;
    }

    public void setProduct_price(String product_price) {
        this.product_price = product_price;
    }

    public int getProduct_discount() {
        return product_discount;
    }

    public void setProduct_discount(int product_discount) {
        this.product_discount = product_discount;
    }
}
