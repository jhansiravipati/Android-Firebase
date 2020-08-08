package com.example.userlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MapsActivity extends AppCompatActivity implements LocationListener {
    GoogleMap map;
    private static final int MY_PERMISSIONS_REQUEST_Access_Location = 9;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double latitude;
    Double longitude;
    private Marker myMarker;
    DatabaseReference database;
    private LatLng latLng;

    ArrayList<LatLng> Latlangs =new ArrayList<LatLng>();
    private LocationManager locationManager;
    // ArrayList<LatLng> longitudes =new ArrayList<LatLng>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        database = FirebaseDatabase.getInstance().getReference().child("Location");


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10 * 1000L, 0, MapsActivity.this);
       // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 4000, 0, MapsActivity.this);
       // locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 4000, 0, MapsActivity.this);
        viewInfoUser();
    }

  /*  void showlocationUser() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                showlocation();
                //  viewInfo();


            }
        });
    }*/
     void viewInfoUser() {
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                showlocation();


            }
        });
    }


    private void showlocation() {

        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MapsActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MapsActivity.this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Require location permission")
                        .setMessage("Allow the given location feature to continue")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MapsActivity.this,
                                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_Access_Location);

                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .create()
                        .show();

            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MapsActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_Access_Location);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted


            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                                if (map != null) {


                                    latLng = new LatLng(latitude, longitude);
                                    Latlangs.add(latLng);



                                    map.addMarker(new MarkerOptions().position(latLng).title("you areee hereee!!1").snippet("and snippet")
                                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                                    map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
                                    viewInfo();


                                }
                            } else {
                                Toast.makeText(MapsActivity.this, "Null pointer :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_Access_Location) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showlocation();


            } else {

            }
        }

    }

    public void viewInfo(){
        if(map!=null){
            for(int i=0;i<Latlangs.size();i++){
                map.addMarker(new MarkerOptions().position(Latlangs.get(i)).title("you areee hereee!!1").snippet("and snippet")
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                map.moveCamera(CameraUpdateFactory.newLatLng(Latlangs.get(i)));
                map.animateCamera(CameraUpdateFactory.newLatLngZoom(Latlangs.get(i), 15));

            }
        }
    }


    @Override
    public void onLocationChanged(Location location) {

        Log.d("Location ; ",""+location.toString());
        if (location != null) {
            // Logic to handle location object
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            if (map != null) {


                latLng = new LatLng(latitude, longitude);
                Latlangs.add(latLng);

                viewInfo();


            }
        } else {
            Toast.makeText(MapsActivity.this, "Null pointer :(", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

