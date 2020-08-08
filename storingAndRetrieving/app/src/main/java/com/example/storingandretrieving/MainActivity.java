package com.example.storingandretrieving;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    Button btnsend,btnget;
    EditText etsend,etget;
    TextView tvget;
    ListView listView;
   DatabaseReference databaseReference;
   ArrayList<String> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnsend= findViewById(R.id.btnsend);
        btnget= findViewById(R.id.btnget);
        etsend=findViewById(R.id.etsend);
        etget=findViewById(R.id.etget);
        listView=findViewById(R.id.listview);
        //tvget=findViewById(R.id.tvget);

        databaseReference= FirebaseDatabase.getInstance().getReference();




        btnsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendtofirebase();
                etsend.setText("");
            }
        });

        btnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retriveFromFirebase();
                etget.setText("");

            }
        });


    }



    private void retriveFromFirebase() {
        list=new ArrayList<>();
        final ArrayAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);
        String textToRetrieve= etget.getText().toString().toLowerCase();

        if(textToRetrieve.contains("factorial")){
            databaseReference.child("factorial").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        list.add(snapshot.getValue().toString());

                    }
                      adapter.notifyDataSetChanged();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else if(textToRetrieve.contains("fibanocci")){
            databaseReference.child("fibanocci").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        list.add(snapshot.getValue().toString());

                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else if (textToRetrieve.contains("android")){
            databaseReference.child("android").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    list.clear();
                    for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                        list.add(snapshot.getValue().toString());

                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
        else{

        }
    }


    private void sendtofirebase() {
        String textToSend = etsend.getText().toString().toLowerCase();



            if(textToSend.contains("factorial")) {
                Toast.makeText(this, "Yes it contains!", Toast.LENGTH_SHORT).show();
                databaseReference.child("factorial").push().setValue(textToSend);

            }
            else  if(textToSend.contains("fibanocci")) {
                Toast.makeText(this, "Yes it contains!", Toast.LENGTH_SHORT).show();
                databaseReference.child("fibanocci").push().setValue(textToSend);

            }
            else  if(textToSend.contains("android")) {
                Toast.makeText(this, "Yes it contains!", Toast.LENGTH_SHORT).show();
                databaseReference.child("android").push().setValue(textToSend);

            }
            else{
                Toast.makeText(this, "Nope text doesn't there", Toast.LENGTH_SHORT).show();

            }


    }
}
