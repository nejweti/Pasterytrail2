package com.nejat.pasterytrail2.Databases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nejat.pasterytrail2.Classes.Order;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 4/1/2018.
 */

public class TOTALORDERDB extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "food_order_db";
    public static final String TABLE_NAME = "order_db";
    public static final String ORDER_ID = "order_id";
    public static final String PRODUCT_NAME = "product_name";
    public static final String PRODUCT_ID = "PRODUCT_ID";
    public static final String PRODUCT_PRICE = "product_price";
    public static final String  PRODUCT_QUANTITY = "product_quantity";
    public static final String PRODUCT_DISCOUNT = "product_discount";

    public TOTALORDERDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "("
                + ORDER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + PRODUCT_ID + " TEXT,"
                + PRODUCT_NAME + " TEXT,"
                + PRODUCT_PRICE + " TEXT,"
                + PRODUCT_QUANTITY + " INTEGER DEFAULT 1,"
                + PRODUCT_DISCOUNT + " INTEGER"

                +")";

        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void addCart(Order order){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(PRODUCT_ID,order.getProduct_id());
        values.put(PRODUCT_NAME,order.getProduct_name());
        values.put(PRODUCT_PRICE,order.getProduct_price());
        values.put(PRODUCT_QUANTITY,order.getProduct_quantity());
        values.put(PRODUCT_DISCOUNT,order.getProduct_discount());
        db.insert(TABLE_NAME,null,values);


    }

    public List<Order> getAllCarts(){
        List<Order> orders = new ArrayList();

        String query = "SELECT * FROM "+ TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                orders.add(new Order(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getInt(5)));
            }
            while(cursor.moveToNext());
        }
        return orders;

    }
}
