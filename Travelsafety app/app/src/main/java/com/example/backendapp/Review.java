package com.example.backendapp;

import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class Review extends AppCompatActivity {


    private EditText et;
    private Button btnreview;
    private TextView tvreview;
   String review;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        et = (EditText) findViewById(R.id.etreview);
        btnreview = (Button) findViewById(R.id.btnreview);
       tvreview = (TextView) findViewById(R.id.tvreview);

       // review= et.getText().toString();

        btnreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Toast.makeText(Review.this, "Your review sent!", Toast.LENGTH_SHORT).show();
                notification();
                //Intent intent = new Intent(getApplicationContext(), Viewreview.class);



                //startActivity(new Intent(Review.this ,MainActivity.class));






            }



        });
    }

    private void notification() {
        Intent intent = new Intent(this,Viewreview.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("review", et.getText().toString());


        PendingIntent pi = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder builder= new NotificationCompat.Builder(this, "Travel Safety app")
                .setSmallIcon(R.drawable.download)
                .setContentTitle("You got a review for your travel")
                .setContentText("A native of the place has given you an update")
                .setContentIntent(pi)
                .setAutoCancel(true)

                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager. notify(999,builder.build());
    }
}