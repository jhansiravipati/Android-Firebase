package com.example.backendapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Getrating extends AppCompatActivity {
    private EditText etfrom , etto ;
    private Button btn;
    private RadioButton rbtnfemale,rbtnmale,rbtnbus,rbtntrain,rbtn;
    RadioGroup rgroup1,rgroup2;
    private TextView resulttv,tvreviewview;
    private String genderentered;
    FirebaseAuth auth;
    private static final String channelid= "Travel Safety app";
    private static final String channelname= "Travel Safety app";
    private static final String channeldisc= "Travel Safety app notification";

    DatabaseReference database;

    int i=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getrating);

        btn=(Button)findViewById(R.id.btn);
        resulttv=(TextView)findViewById(R.id.resulttv);
        etfrom=(EditText)findViewById(R.id.etfrom);
        etto= (EditText)findViewById(R.id.etto);
        rbtnfemale=(RadioButton)findViewById(R.id.rbtnfemale);
        rgroup1= (RadioGroup)findViewById(R.id.rgroup1);
        rbtnmale=(RadioButton)findViewById(R.id.rbtnmale);
        rgroup2=(RadioGroup)findViewById(R.id.rgroup2);
        rbtnbus=(RadioButton)findViewById(R.id.rbtnbus);
        rbtntrain=(RadioButton)findViewById(R.id.rbtntrain);
        //tvreviewview=(TextView)findViewById(R.id.tvreviewview);
       // final TextView userid = (TextView)findViewById(R.id.userid);

        database= FirebaseDatabase.getInstance().getReference().child("info");

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel =new NotificationChannel(channelid,channelname, NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(channeldisc);

            NotificationManager manageroreo = getSystemService(NotificationManager.class);

            manageroreo.createNotificationChannel(channel);
        }


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  final String fromentered = etfrom.getText().toString();
                final String toentered = etto.getText().toString();
                int selectedid = rgroup1.getCheckedRadioButtonId();
                rbtn = (RadioButton)findViewById(selectedid);

                genderentered = rbtn.getText().toString();








                database.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {

                            String todb =ds.child("to").getValue(String.class);
                            String genderdb =ds.child("gender").getValue(String.class);
                            String currentFirebaseUser =auth.getInstance().getCurrentUser().getUid() ;
                         //  FirebaseMessaging.getInstance().subscribeToTopic(currentFirebaseUser)


                                if (toentered.equals(todb) && genderentered.equals(genderdb) ) {
                                    String value = ds.child("rating").getValue().toString();
                                    resulttv.setText(value);
                                    notication();
                                }
                                else{
                                    Toast.makeText(Getrating.this, "No match", Toast.LENGTH_SHORT).show();
                                }












                            // userid.setText(currentFirebaseUser);
                                   // FirebaseMessaging.getInstance().subscribeToTopic(toentered);







                            //Attributes a= dataSnapshot.getValue(Attributes.class);
                            //String value = String.valueOf(Attributes.rating);
                            //resulttv.setText(value);



                               //String value = dataSnapshot.getValue(String.class);
                                //
                        }
                   }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });
        //showdata =FirebaseDatabase.getInstance().getReference("Info");
        // Query checkfrom = showdata.orderByChild("from").equalTo(fromentered);
        // Query checkfrom = showdata.orderByChild("from").equalTo(fromentered);
        //Query checkto = showdata.orderByChild("to").equalTo(toentered);



                //final
                //final String toentered = etto.getText().toString();

               /* showdata.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {


                       /* for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            Toast.makeText(Getrating.this, "enteredin", Toast.LENGTH_SHORT).show();
                            Attributes a = new Attributes();
                            float result= a.setRating(ds.child(String.valueOf(i+1)).getValue(Attributes.class).getRating());
                            String resultt = String.valueOf(result);
                            resulttv.setText(resultt);
                           /*
                            i = (int) dataSnapshot.getChildrenCount();
                            String fromdb = dataSnapshot.child(String.valueOf(i + 1)).child("from").getValue(String.class);
                            String todb = dataSnapshot.child(String.valueOf(i + 1)).child("to").getValue(String.class);

                            if (fromentered.equals(fromdb) && toentered.equals(todb)) {
                                String ratingdb = dataSnapshot.child(String.valueOf(i + 1)).child("rating").getValue(String.class);
                                resulttv.setText(ratingdb);
                                Toast.makeText(Getrating.this, "retrieving successful", Toast.LENGTH_SHORT).show();

                            }
                            else{
                                Toast.makeText(Getrating.this, "No match", Toast.LENGTH_SHORT).show();
                            }



                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });*/

            }

    private void notication() {
        Intent intent = new Intent(this,Review.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent pi = PendingIntent.getActivity(this, 0 , intent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this, channelid)
                .setSmallIcon(R.drawable.download)
                .setContentTitle("Would you like to give any review")
                .setContentText("A User is arriving at your native place. So, Would you like to give any review")
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                ;

        NotificationManagerCompat manager = NotificationManagerCompat.from(this);
        manager.notify(999,builder.build());
    }



}

