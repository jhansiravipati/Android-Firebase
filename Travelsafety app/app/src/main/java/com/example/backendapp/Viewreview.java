package com.example.backendapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Viewreview extends AppCompatActivity {

    TextView tvshowreview ;
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewreview);
        tvshowreview=(TextView)findViewById(R.id.tvshowreview);
        btn = (Button)findViewById(R.id.btn);



       // Review r= new Review();
        //String showreview = r.review;


       // tvshowreview.setText(showreview);

        tvshowreview.setText(getIntent().getStringExtra("review"));

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Viewreview.this, MainActivity.class));
            }
        });

    }
}
