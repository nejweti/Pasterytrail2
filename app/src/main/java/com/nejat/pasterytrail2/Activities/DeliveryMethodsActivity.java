package com.nejat.pasterytrail2.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nejat.pasterytrail2.Classes.PastryLocation;
import com.nejat.pasterytrail2.R;

public class DeliveryMethodsActivity extends AppCompatActivity implements View.OnClickListener {
    private Button deliverybtn, pickupbtn, tablebtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_methods);

        deliverybtn = (Button) findViewById(R.id.btn_delivery);
        pickupbtn = (Button) findViewById(R.id.btn_pickup);
        tablebtn = (Button) findViewById(R.id.btn_fromtable);

        pickupbtn.setOnClickListener(this);
        deliverybtn.setOnClickListener(this);
        tablebtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == pickupbtn) {
            startActivity(new Intent(DeliveryMethodsActivity.this, PasteryListActivity.class));
//            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("location");
//            GeoFire geoFire = new GeoFire(ref);
//
//            geoFire.setLocation(ref.getKey(), new GeoLocation(57.7853889, -122.4056973), new GeoFire.CompletionListener() {
//                @Override
//                public void onComplete(String key, DatabaseError error) {
//                    if (error != null) {
//                        Log.e("errorrr","There was an error saving the location to GeoFire: " + error);
//                    } else {
//                        Log.i("sucess","Location saved on server successfully!");
//                    }
//                }
//            });
        }
    }
}
