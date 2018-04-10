package com.nejat.pasterytrail2.Activities;

import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.database.FirebaseDatabase;
import com.nejat.pasterytrail2.Databases.CURRENTORDERDB;
import com.nejat.pasterytrail2.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        clearDatabase();
    }

    public void clearDatabase(){
        CURRENTORDERDB orderDb = new CURRENTORDERDB(this);
        SQLiteDatabase db  = orderDb.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS" + orderDb.TABLE_NAME);
        orderDb.onCreate(db);
        db.close();
    }
}
