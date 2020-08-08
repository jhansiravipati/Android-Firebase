package com.example.ridesharing;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class Createride extends AppCompatActivity {
    EditText etname,etdate,etfrom,etto;
    Button btnset;
    String name,from, to, date;
    DatabaseReference databaseReference;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createride);
        etname=findViewById(R.id.etname);
        etdate=findViewById(R.id.etdate);
        etfrom=findViewById(R.id.etfrom);
        etto=findViewById(R.id.etto);
        btnset=findViewById(R.id.btnset);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Rides");

        btnset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name=etname.getText().toString();
                from=etfrom.getText().toString();
                to=etto.getText().toString();
                date=etdate.getText().toString();

                AlertDialog.Builder builder= new AlertDialog.Builder(Createride.this);
                builder.setMessage("Are you sure?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Attributes a=new Attributes(date, name,from,to);

                        a.setName(name);
                        a.setDate(date);
                        a.setFrom(from);
                        a.setTo(to);
                        databaseReference.push().setValue(a);

                        Toast.makeText(Createride.this, "Your ride is created!", Toast.LENGTH_LONG).show();
                    }
                }).setNegativeButton("cancel",null);

                AlertDialog alert=builder.create();
                alert.show();
            }
        });



    }
}
