package com.example.backendapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static com.example.backendapp.Attributes.rating;

public class Giverating extends AppCompatActivity {

    private EditText et1, et2;
    private Button b1;
    RadioGroup rgroup1,rgroup2;
     RadioButton rbtnmale,rbtnfemale;
     RadioButton rbtnbus,rbtntrain;
     RatingBar r;
     int i=0;

    DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_give);



       // FirebaseDatabase.getInstance().setPersistenceEnabled(true);
         database = FirebaseDatabase.getInstance().getReference("info");
        et1= (EditText)findViewById(R.id.fromet);
        et2= (EditText)findViewById((R.id.toet));
        b1= (Button)findViewById(R.id.btn);
        rbtnfemale=(RadioButton)findViewById(R.id.rbtnfemale);
        rgroup1= (RadioGroup)findViewById(R.id.rgroup1);
        rbtnmale=(RadioButton)findViewById(R.id.rbtnmale);
        rgroup2=(RadioGroup)findViewById(R.id.rgroup2);
        rbtnbus=(RadioButton)findViewById(R.id.rbtnbus);
        rbtntrain=(RadioButton)findViewById(R.id.rbtntrain);
        r= (RatingBar)findViewById(R.id.r);


        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    i= (int)dataSnapshot.getChildrenCount();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

       b1.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               String from = et1.getText().toString();
               String to= et2.getText().toString();
               String male = rbtnmale.getText().toString();
               String female = rbtnfemale.getText().toString();
               String bus = rbtnbus.getText().toString();
               String train= rbtntrain.getText().toString();
               rating = (long) (2*(r.getRating()));



               Attributes a= new Attributes(from,to);
               database.child(String.valueOf(i+1)).setValue(a);
               a.setRating(rating);
               database.child(String.valueOf(i+1)).setValue(a);

               if(rbtnmale.isChecked()){
                   a.setGender(male);
                   database.child(String.valueOf(i+1)).setValue(a);
               }
               else{
                   a.setGender(female);
                   database.child(String.valueOf(i+1)).setValue(a);
               }

               if(rbtnbus.isChecked()){
                   a.setMode_of_travel(bus);
                   database.child(String.valueOf(i+1)).setValue(a);
               }
               else{
                   a.setMode_of_travel(train);
                   database.child(String.valueOf(i+1)).setValue(a);
               }
               Toast.makeText(Giverating.this , "Your rainting is submitted" , Toast.LENGTH_SHORT).show();
               startActivity(new Intent(Giverating.this, MainActivity.class));


           }
       });

    }
}
//database.child(String.valueOf(i+1)).setValue(a);