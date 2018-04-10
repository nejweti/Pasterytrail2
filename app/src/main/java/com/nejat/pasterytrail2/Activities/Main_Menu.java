package com.nejat.pasterytrail2.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nejat.pasterytrail2.Adapters.MenuCatagoryAdapter;
import com.nejat.pasterytrail2.Adapters.NavigationAdapter;
import com.nejat.pasterytrail2.Adapters.ShoppingCartAdapter;
import com.nejat.pasterytrail2.Classes.MenuModel;
import com.nejat.pasterytrail2.Classes.NavData;
import com.nejat.pasterytrail2.Classes.User;
import com.nejat.pasterytrail2.Databases.CURRENTORDERDB;
import com.nejat.pasterytrail2.Fragments.CatagoriesFragment;
import com.nejat.pasterytrail2.Manifest;
import com.nejat.pasterytrail2.R;
import com.nejat.pasterytrail2.ViewHolder.Category;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Main_Menu extends AppCompatActivity implements View.OnClickListener {

    List<NavData> navDataList;
    ListView listView;
    RecyclerView recyclerView;
    MenuCatagoryAdapter mMenuCatagoryAdapter;
    FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    FirebaseRecyclerOptions<MenuModel> options;
    FirebaseRecyclerAdapter adapter;
    FloatingActionButton totalCart;
    boolean mLocationPermissionGranted = false;
    private final int LOCATION_PERMISSION_CODE = 1234;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main__menu);
        navDataList = new ArrayList<>();
        navDataList.add(new NavData(R.drawable.ic_home_black_24dp, "Menus"));
        navDataList.add(new NavData(R.drawable.ic_shopping_cart_black_24dp, "Shopping Cart"));
        navDataList.add(new NavData(R.drawable.ic_favorite_border_black_24dp, "Favorites"));
        navDataList.add(new NavData(R.drawable.ic_reorder_black_24dp, "Orders"));

        user = (User) getApplicationContext();
        clearDatabase();
        isGoogleAPIAvailable();
        getLocationPermission();
        listView = (ListView) findViewById(R.id.nav_drawer_list_view);
        NavigationAdapter navigationAdapter = new NavigationAdapter(this, navDataList);
        listView.setAdapter(navigationAdapter);

        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("category");
        Log.i("databse", databaseReference.toString());
        totalCart = (FloatingActionButton) findViewById(R.id.total_cart);
        totalCart.setOnClickListener(this);
        recyclerView = (RecyclerView) findViewById(R.id.menu_categories);

        options = new FirebaseRecyclerOptions.Builder<MenuModel>()
                .setQuery(databaseReference, MenuModel.class)
                .build();

        Log.i("options", options.toString());
        loadCategory();
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
        Log.i("exc", "ted");
    }


    private void loadCategory() {
        adapter = new FirebaseRecyclerAdapter<MenuModel, Category>(options) {
            @Override
            public Category onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_catagories_linear, parent, false);
                Log.i("excuted", "excuteddd");
                return new Category(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Category holder, final int position, @NonNull MenuModel model) {
                final ProgressBar bar = new ProgressBar(Main_Menu.this);
                bar.setVisibility(View.VISIBLE);
                holder.menu_name.setText(model.getName());
                Picasso.with(Main_Menu.this).load(model.getImage())
                        .into(holder.menu_image, new com.squareup.picasso.Callback() {
                            @Override
                            public void onSuccess() {
                                if (bar != null) {
                                    bar.setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onError() {

                            }
                        });
                holder.menu_linear.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Log.i("menu", "isclicked");
                        adapter.getRef(position).getKey();
                        user.setCategoryId(adapter.getRef(position).getKey());
                        startActivity(new Intent(Main_Menu.this, FoodActivity.class));
                        Log.i("category", user.getCategoryId());
                    }
                });
            }
        };


    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    @Override
    public void onClick(View v) {
        if (v == totalCart) {
            startActivity(new Intent(Main_Menu.this, ShoppingCartActivity.class));
        }
    }

    public void clearDatabase() {
        CURRENTORDERDB orderDb = new CURRENTORDERDB(this);
        SQLiteDatabase db = orderDb.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + orderDb.TABLE_NAME);
        orderDb.onCreate(db);
        db.close();
    }

    public void isGoogleAPIAvailable(){
        int availability = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if(availability == ConnectionResult.SUCCESS){
            Log.i("Google API","Succefully installed");
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(availability)){
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this,availability,LOCATION_PERMISSION_CODE);
            Log.i("Google Api","Fixable Error");
        }
        else
            Toast.makeText(this,"Google API Is not available",Toast.LENGTH_LONG).show();
    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */

        String[] permissions = {
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_NETWORK_STATE,
                android.Manifest.permission.INTERNET,

        };
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this.getApplicationContext(), android.Manifest.permission.INTERNET) == PackageManager.PERMISSION_GRANTED) {
                        mLocationPermissionGranted = true;

                    } else {
                        ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_CODE);
                    }
                } else {
                    ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_CODE);

                }
            } else {
                ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_CODE);
            }

        } else {
            ActivityCompat.requestPermissions(this, permissions, LOCATION_PERMISSION_CODE);

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case LOCATION_PERMISSION_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            Log.i("Location", "Permission Failed");
                            mLocationPermissionGranted = false;
                        }

                    }
                    Log.i("Location", "Permission Granted");
                    mLocationPermissionGranted = true;
                }
            }
        }
    }
}
