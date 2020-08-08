package com.example.userlocation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.ContentResolver;
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
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.net.URI;

public class MainActivity extends AppCompatActivity {
    private static final int MY_PERMISSIONS_REQUEST_Access_Location = 9;
    private static final int PICK_IMAGE_REQUEST = 99;
    private static final int PERMISSION_CODE = 101;
    public static final int cam_REQUEST_CODE = 99;
    private Button btnsubmit,btnpred;
    private TextView tvlatitude, tvlongitude,tvid;
    private FusedLocationProviderClient fusedLocationClient;
    ImageView iv;
    Button btnchoose, btnmap,btninfo,btnid;
    private Uri mimageuri;
    String[] permissions_all = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
    private int permission_code = 101;
    LocationManager locationManager;
    boolean isGPSLocation;
    boolean isNetworkLocation;
    Location loc;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    Double latitude;
    Double longitude;
    GoogleMap map;
    String latlang;
    FirebaseAuth auth;
    ProgressDialog progressDialog ;
    private static final String channelid= "Waste Management app";
    private static final String channelname= "Waste Management app";
    private static final String channeldisc= "channel_disc";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        btnsubmit = (Button) findViewById(R.id.btn);
        //tvlatitude = (TextView) findViewById(R.id.tvlatitude);
        //tvlongitude = (TextView) findViewById(R.id.tvlongitude);
        btnchoose = (Button) findViewById(R.id.btncam);btninfo = (Button) findViewById(R.id.btninfo);
        btnpred = (Button) findViewById(R.id.btnpred);
      //  btnmap = (Button) findViewById(R.id.btnmap);
        progressDialog = new ProgressDialog(MainActivity.this);

        iv = (ImageView) findViewById(R.id.iv);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Location");


        btnchoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                opengallery();
            }
        });

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadLocation();
                uploadimage();

            }
        });
        btninfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,FirebaseMap.class));
            }
        });

        btnpred.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();
                startActivity(new Intent(MainActivity.this, PredictActivity.class));
            }
        });

/*btninfo.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//
//        MapsActivity mpp=new MapsActivity();
//        if(mpp.map != null) {
//            mpp.viewInfoUser();
//        }
       startActivity(new Intent(MainActivity.this,MapsActivity.class));
    }
});
*/
      
   }

    private void sendNotification() {

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel(channelid,channelname, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channeldisc);

            NotificationManager manageroreo = getSystemService(NotificationManager.class);

            manageroreo.createNotificationChannel(channel);
        }

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this, channelid)
                .setSmallIcon(R.drawable.download)
                .setContentTitle("Predicted Result")
                .setContentText("62,000")
                .setAutoCancel(true)
                .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText("Expected volume of waste at the selected location by next year would be 62,000"))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                ;

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999,builder.build());

    }

    private String getFileExt(Uri uri){
       ContentResolver contentResolver= getContentResolver() ;
       MimeTypeMap mimeTypeMap= MimeTypeMap.getSingleton();
       return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri
       ));

   }

    private void opengallery() {
        Intent intent =new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"select image"),PICK_IMAGE_REQUEST);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PICK_IMAGE_REQUEST && resultCode==RESULT_OK ){
            if(data!=null && data.getData()!=null){
                mimageuri=data.getData();
                Picasso.get().load(mimageuri).into(iv);
            }
        }
    }

    private void uploadLocation() {


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
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
                                ActivityCompat.requestPermissions(MainActivity.this,
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
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_Access_Location);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted


            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {
                                // Logic to handle location object
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();

                            }
                            else{
                                Toast.makeText(MainActivity.this, "Null pointer :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==MY_PERMISSIONS_REQUEST_Access_Location){
            if(grantResults.length>0  && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                uploadLocation();


            }
            else{

            }
        }
    }


    private void uploadimage() {
        if(mimageuri !=null){
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            StorageReference filereference = storageReference.child(System.currentTimeMillis()+" "+getFileExt(mimageuri));
            filereference.putFile(mimageuri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Upload Successfull!!", Toast.LENGTH_SHORT).show();
                    Uploaddata uploaddata =new Uploaddata(latitude, longitude, taskSnapshot.getUploadSessionUri().toString());

                    String uploadId=databaseReference.push().getKey();
                    databaseReference.child(uploadId).setValue(uploaddata);
                   //databaseReference.child(uploadId).setValue(latlang);
                    //Toast.makeText(MainActivity.this, "latlan also uploaded", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "upload failed:(", Toast.LENGTH_SHORT).show();

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {

                }
            });

        }else{
            Toast.makeText(this, "Choose something ", Toast.LENGTH_SHORT).show();
        }
    }

    }


   

