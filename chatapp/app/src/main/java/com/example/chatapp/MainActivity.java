package com.example.chatapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.ColorSpace;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private SearchView searchView;
    private EditText editText;
    DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    ArrayList<String> questionslist;
    private Button btnhelp;


    private FirebaseRecyclerOptions<Model> options;
    private FirebaseRecyclerAdapter<Model, MyViewHolder> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         btnhelp=findViewById(R.id.btnhelp);
        editText = findViewById(R.id.edittext);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("question_paper");
        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

       btnhelp.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        openUsers();
    }
});


        //searchFirebase("");


        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    searchFirebase(s.toString());
                }
                else
                {
                    searchFirebase("");
                }

            }


        });



    }

    private void openUsers() {
        startActivity(new Intent(MainActivity.this,Users.class));
    }

    private void searchFirebase(String s) {
        Query query=databaseReference.orderByChild("question").startAt(s).endAt(s+"\uf8ff");
        options = new FirebaseRecyclerOptions.Builder<Model>().setQuery(query, Model.class).build();
        adapter = new FirebaseRecyclerAdapter<Model, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
                Toast.makeText(MainActivity.this, getItemCount()+"", Toast.LENGTH_SHORT).show();
                holder.textViewques.setText(""+model.getQuestion());

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.holder,parent,false);
                return new MyViewHolder(v);
            }
        };
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

}

