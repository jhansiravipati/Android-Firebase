package com.example.markonmap;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Service;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.markonmap.MapActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

public class MainActivity extends AppCompatActivity {


    private static final int MY_PERMISSIONS_REQUEST_CAMERA =9 ;
    private Button btn;
    private TextView tvlatitude, tvlongitude;
    private FusedLocationProviderClient fusedLocationClient;
    ImageView iv;
    Button btncam, btnmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button) findViewById(R.id.btn);
        tvlatitude = (TextView) findViewById(R.id.tvlatitude);
        tvlongitude = (TextView) findViewById(R.id.tvlongitude);
        btncam = (Button) findViewById(R.id.btncam);
        btnmap = (Button) findViewById(R.id.btnmap);
        iv = (ImageView) findViewById(R.id.iv);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        btnmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(this);
                startActivity(new Intent(MainActivity.this, MapActivity.class));
            }
        });
        btncam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera();
            }
        });

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {






            }
        });


    }


    private void camera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.CAMERA},
                    MY_PERMISSIONS_REQUEST_CAMERA);
        } else {
            opencamera();
        }
        ;

    }

    private void opencamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, 99);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_PERMISSIONS_REQUEST_CAMERA) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            iv.setImageBitmap(image);


        }
    }

}