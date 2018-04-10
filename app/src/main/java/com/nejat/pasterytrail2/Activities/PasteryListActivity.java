package com.nejat.pasterytrail2.Activities;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.os.Looper;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.geofire.GeoFire;
import com.firebase.geofire.GeoLocation;
import com.firebase.geofire.GeoQuery;
import com.firebase.geofire.GeoQueryEventListener;
import com.firebase.geofire.util.Constants;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nejat.pasterytrail2.Adapters.NearbyAdapter;
import com.nejat.pasterytrail2.Classes.PastryLocInfo;
import com.nejat.pasterytrail2.Classes.PastryLocation;
import com.nejat.pasterytrail2.Classes.User;
import com.nejat.pasterytrail2.R;
import com.nejat.pasterytrail2.ViewHolder.PasteryHolder;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class PasteryListActivity extends AppCompatActivity implements View.OnClickListener {
    RecyclerView mrecyclerView;
    TextView namePastry;
    Button locateMebtn;
    android.support.v7.widget.SearchView mSearchView;
    FirebaseRecyclerOptions<PastryLocInfo> options;
    FirebaseRecyclerAdapter adapter;
    boolean mLocationPermissionGranted = false;
    private final int LOCATION_PERMISSION_CODE = 1234;
    FusedLocationProviderClient mFusedLocationProviderClient;
    public Double Latitude;
    public Double Longitude;
    LatLng currentPoint;
    LatLng endPoint;
    User user;
    PlaceAutocompleteFragment autocompleteFragment;
    LocationRequest mLocationRequest;
    public static List<PastryLocation> locationList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patery_list);

//        locateMebtn = (Button) findViewById(R.id.locatemebtn);
//        mSearchView = (android.support.v7.widget.SearchView) findViewById(R.id.searchLocbtn);

//        locateMebtn.setOnClickListener(this);
        user = (User) getApplicationContext();
        isGoogleAPIAvailable();
        statusCheck();
        getLocationPermission();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("Pastry_info_Location");

        options = new FirebaseRecyclerOptions.Builder<PastryLocInfo>()
                .setQuery(query, PastryLocInfo.class)
                .build();

        mrecyclerView = (RecyclerView) findViewById(R.id.pastry_recyclerview);
        namePastry = (TextView) findViewById(R.id.pastry_name);
        loadData();
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mrecyclerView.setAdapter(adapter);



        autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                User user = (User) getApplicationContext();
                user.setLatitude(place.getLatLng().latitude);
                user.setLongitude(place.getLatLng().longitude);
                Log.i("Placauto" , place.getName().toString());
                geofireCall();
            }

            @Override
            public void onError(Status status) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }


    private void loadData() {

        adapter = new FirebaseRecyclerAdapter<PastryLocInfo, PasteryHolder>(options) {

            @Override
            public PasteryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                Log.i("pasexc", "yes");
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.linear_pastery_layout, parent, false);
                return new PasteryHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull PasteryHolder holder, int position, @NonNull PastryLocInfo model) {
                holder.pastry_name.setText(model.getName());

                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Pastry_info_Location");
                GeoFire geoFire = new GeoFire(ref);




            }
        };
    }


    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onClick(View view) {
//        if (view == locateMebtn) {
//            getDeviceLocation();
//            Log.i("l","buttonclicked");
////            geofireCall();
//
//
//
//        }
    }

    public void isGoogleAPIAvailable() {
        int availability = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this);
        if (availability == ConnectionResult.SUCCESS) {
            Log.i("Google API", "Succefully installed");
        } else if (GoogleApiAvailability.getInstance().isUserResolvableError(availability)) {
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(this, availability, LOCATION_PERMISSION_CODE);
            Log.i("Google Api", "Fixable Error");
        } else
            Toast.makeText(this, "Google API Is not available", Toast.LENGTH_LONG).show();
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

    public void getDeviceLocation() {
        try {
            if (mLocationPermissionGranted) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                checkForLocationRequest();
                mFusedLocationProviderClient.requestLocationUpdates(mLocationRequest, new LocationCallback() {

                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        super.onLocationResult(locationResult);
                       Longitude =  locationResult.getLastLocation().getLongitude();
                       Latitude = locationResult.getLastLocation().getLatitude();
                       currentPoint = new LatLng(Latitude,Longitude);
                        getLocFromAddress(Latitude, Longitude);

//                        retrieveLatLong();
//                        mSearchView.setQuery(Latitude + "," + Longitude,true);

                    }

                }, Looper.myLooper());            }

        } catch (Exception e) {
            Log.e("LocationException", e.getMessage());
        }
    }

    public void checkForLocationRequest() {
        mLocationRequest = LocationRequest.create();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(1234);
        mLocationRequest.setFastestInterval(123);
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }


//    public void retrieveLatLong(){
//       final List<Double> nearby = new ArrayList<>();
//
//        FirebaseDatabase.getInstance().getReference().child("geofire_location")
//            .addListenerForSingleValueEvent(new ValueEventListener() {
//        @Override
//        public void onDataChange(DataSnapshot dataSnapshot) {
//            for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
//                PastryLocInfo data = snapshot.getValue(PastryLocInfo.class);
//                Latitude = data.getLatitude();
//                Longitude = data.getLongitude();
//                endPoint = new LatLng(Latitude,Longitude);
//                Log.i("crntpnt",currentPoint.latitude+"");
//                if(CalculationByDistance(currentPoint,endPoint) < 0.126){
//                    nearby.add(CalculationByDistance(currentPoint,endPoint));
//                }
//                Log.i("nearbylist",nearby.size()+"");
//                Log.i("nearby0",nearby.get(0)+"");
//                Log.i("coordinatesss",data.getLatitude()+",,"+data.getLongitude()+"");
//            }
//        }
//        @Override
//        public void onCancelled(DatabaseError databaseError) {
//        }
//    });}

//    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
//        int Radius = 1;// radius of earth in Km
//        double lat1 = StartP.latitude;
//        double lat2 = EndP.latitude;
//        double lon1 = StartP.longitude;
//        double lon2 = EndP.longitude;
//        double dLat = Math.toRadians(lat2 - lat1);
//        double dLon = Math.toRadians(lon2 - lon1);
//        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
//                + Math.cos(Math.toRadians(lat1))
//                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
//                * Math.sin(dLon / 2);
//        double c = 2 * Math.asin(Math.sqrt(a));
//        double valueResult = Radius * c;
//        double km = valueResult / 1;
//        DecimalFormat newFormat = new DecimalFormat("####");
//        int kmInDec = Integer.valueOf(newFormat.format(km));
//        double meter = valueResult % 1000;
//        int meterInDec = Integer.valueOf(newFormat.format(meter));
//        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
//                + " Meter   " + meterInDec);
//
//        return Radius * c;
//    }

    public void geofireCall(){
        //        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("pasty_location");
        final GeoFire geoFire = new GeoFire(FirebaseDatabase.getInstance().getReference().child("geofire_location"));
        
        user = (User) getApplicationContext();
        GeoQuery geoQuery = geoFire.queryAtLocation(new GeoLocation(user.getLatitude(), user.getLongitude()), 10);
        Log.i("onkeygeo",user.getLatitude()+"");

        geoQuery.addGeoQueryEventListener(new GeoQueryEventListener() {
            @Override
            public void onKeyEntered(String key, final GeoLocation location) {
                locationList = new ArrayList<>();
                Log.i("onkeygeo",key+""+location.latitude);
                Query query = FirebaseDatabase.getInstance().getReference().child("Pastry_info_Location").orderByChild("geofire_location").equalTo(key);

                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            PastryLocation loc = snapshot.getValue(PastryLocation.class);
                            Log.i("pasloca",loc.getName());

                            locationList.add(new PastryLocation(loc.getName(),loc.getTables()));
                            Log.i("pasloca", locationList.size()+"");



                        }
                        NearbyAdapter nearbyAdapter = new NearbyAdapter(PasteryListActivity.this,locationList);
                        mrecyclerView.setAdapter(nearbyAdapter);

//                        Log.i("pasloca", locationList.size()+"");
//                        Log.i("pasloca", user.getNearbyPastery().size()+"");


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }

            @Override
            public void onKeyExited(String key) {

            }

            @Override
            public void onKeyMoved(String key, GeoLocation location) {

            }

            @Override
            public void onGeoQueryReady() {

            }

            @Override
            public void onGeoQueryError(DatabaseError error) {

            }
        });
    }

    public void getLocFromAddress( double latitude , double longitude){
        String addressName = mSearchView.getQuery().toString();
        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        List<Address> addresses = null;
        try {
             addresses = geocoder.getFromLocation(latitude,longitude,1);
            Address address = addresses.get(0);
            ArrayList<String> addressFragments = new ArrayList<String>();

                addressFragments.add(address.getAddressLine(0));
                autocompleteFragment.setText(addressFragments.get(0));
                Log.i("addreslist",addressFragments.get(0));




        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
