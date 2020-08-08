package com.example.userlocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.internal.location.zzas;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PredictActivity extends AppCompatActivity {
    DatabaseReference databaseReference, databaseReferencee;
    GoogleMap map;
    private static final int MY_PERMISSIONS_REQUEST_Access_Location = 9;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double latitude;
    Double longitude;
    private Marker myMarker;
    private LatLng latLng;
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("Location");
         databaseReferencee=FirebaseDatabase.getInstance().getReference().child("Location");


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);


        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        supportMapFragment.getMapAsync(new OnMapReadyCallback() {


            @Override
            public void onMapReady(GoogleMap googleMap) {
                map = googleMap;
                MarkOnMap();
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                       // uploadLocation();
                        return false;
                    }
                });
            }
        });
    }

    private void uploadLocation() {


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(PredictActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(PredictActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                new AlertDialog.Builder(this)
                        .setTitle("Require location permission")
                        .setMessage("Allow the given location feature to continue")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(PredictActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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
                ActivityCompat.requestPermissions(PredictActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
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
                                Uploaddata ud =new Uploaddata(latitude,longitude);
                                databaseReferencee.child("latlang").setValue(ud);


                            } else {
                                Toast.makeText(PredictActivity.this, "Null pointer :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == MY_PERMISSIONS_REQUEST_Access_Location) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                uploadLocation();


            } else {

            }
        }
    }


    private void MarkOnMap() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(PredictActivity.this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(PredictActivity.this,
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
                                ActivityCompat.requestPermissions(PredictActivity.this,
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
                ActivityCompat.requestPermissions(PredictActivity.this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        MY_PERMISSIONS_REQUEST_Access_Location);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted

            databaseReference.addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    if (dataSnapshot.hasChild("latitude") && dataSnapshot.hasChild("longitude")) {
                        LatLng location = new LatLng(
                                dataSnapshot.child("latitude").getValue(Long.class),
                                dataSnapshot.child("longitude").getValue(Long.class)
                        );
                        int volume= 62000;
                        map.addMarker(new MarkerOptions()
                                .position(location)
                                .title("Predicted waste at this location is"+volume));

                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(18.5204,73.8567))
                                .title("Predicted volume of waste at this location is "+volume)
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));


                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(15.5119511,80.0317834))
                                .title(volume+"Predicted waste at this location is available now")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));

                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(16.5062, 80.6480))
                                .title(volume+"Predicted waste at this location is available now")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));

                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(10.9027,76.9006))
                                .title(volume+"Predicted waste at this location is available now")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
                        //.icon(BitmapDescriptorFactory.fromResource(R.drawable.redpinn)));
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(9.9816,76.2999))
                                .title(volume+"Predicted waste at this location is available now")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        map.addMarker(new MarkerOptions()
                                .position(new LatLng(13.0827,80.2707))
                                .title("Predicted waste at this location is available now")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));


                    } else {
                        Toast.makeText(PredictActivity.this, "No lat lang in database", Toast.LENGTH_SHORT).show();
                    }
                }


                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }
}
