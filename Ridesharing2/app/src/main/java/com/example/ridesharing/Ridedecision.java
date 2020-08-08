package com.example.ridesharing;

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

import static com.example.ridesharing.R.layout.list_view;
import static com.example.ridesharing.R.layout.single_view_layout;

public class Ridedecision extends AppCompatActivity {
    TextView tvdate,tvfrom,tvto,tvname,tvmembers,tvtv;
    Button btnok,btnjoin;
    DatabaseReference databaseReference;
    EditText etextraname;
    ListView listView;
    String[] ridemembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ridedecision);

        tvdate=findViewById(R.id.tvdate);
        tvname=findViewById(R.id.tvname);
        tvfrom=findViewById(R.id.tvfrom);
        tvto=findViewById(R.id.tvto);
        btnok=findViewById(R.id.btnok);
        btnjoin=findViewById(R.id.btnjoin);
        etextraname=findViewById(R.id.etextraname);
        tvmembers=findViewById(R.id.tvmembers);
        tvtv=findViewById(R.id.tvtv);
       // listView=findViewById(R.id.listview);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Rides");


        final String key=getIntent().getStringExtra("key");

        databaseReference.child(key).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object object= snapshot.child("date").getValue();
                String name= snapshot.child("name").getValue().toString();
                String from= snapshot.child("from").getValue().toString();
                String to= snapshot.child("to").getValue().toString();

                tvdate.setText(""+object);
                tvname.setText(name);
                tvfrom.setText(from);
                tvto.setText(to);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
//
//        final ArrayList<String> list =new ArrayList<>();
//        final ArrayAdapter adapter= new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, list);
//        listView.setAdapter(adapter);

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etextraname.setVisibility(View.VISIBLE);
                tvmembers.setVisibility(View.VISIBLE);
                tvtv.setVisibility(View.VISIBLE);
                btnjoin.setVisibility(View.VISIBLE);
               // listView.setVisibility(View.VISIBLE);
                String name= etextraname.getText().toString();

                final DatabaseReference db= FirebaseDatabase.getInstance().getReference().child("Rides").child(key).child("extraname");

                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                       // list.clear();
                       // for(DataSnapshot ds: snapshot.getChildren()){
                            //list.add(ds.getValue().toString());
                        if(snapshot.exists()) {
                            ridemembers = snapshot.getValue().toString().split(",");
                            tvmembers.setText("");
                            etextraname.setText("");
                            //}
                            for (int i = 0; i < ridemembers.length; i++) {
                                String[] finalmessage = ridemembers[i].split("=");
                                tvmembers.append(finalmessage[1]+"\n");

                            }
                        }else{
                            tvmembers.setText("No team members yet, other than creator");
                        }

                        //adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Ridedecision.this, "database eroor", Toast.LENGTH_SHORT).show();

                    }
                });


            }
        });




        btnjoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String enteredname=etextraname.getText().toString();
                databaseReference.child(key).child("extraname").push().setValue(enteredname);
            }
        });

//These lines should be inside value event listener. Since ridemembers array is giving null pointer exception
//                        }else {
//                            String[] ridemembers = snapshot.getValue().toString().split(",");
//
//                            for (int i = 0; i < ridemembers.length; i++) {
//                                String[] finalmessage = ridemembers[i].split(",");
//                                tvmembers.setText(finalmessage[i] + " \n");
//
//                            }


    }
}
