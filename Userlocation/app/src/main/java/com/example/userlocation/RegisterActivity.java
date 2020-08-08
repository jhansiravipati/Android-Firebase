package com.example.userlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private Button btnlogin,btnreg;
    private EditText etemail,etpswd;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        btnlogin=(Button)findViewById(R.id.btnlogin);
        btnreg=(Button)findViewById(R.id.btnreg) ;
        etemail=(EditText)findViewById(R.id.etemail);
        etpswd=(EditText)findViewById(R.id.etpswd);
        auth=FirebaseAuth.getInstance();

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this , LoginActivity.class));
            }
        });

        btnreg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=etemail.getText().toString();
                String password=etpswd.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(password)){
                    Toast.makeText(RegisterActivity.this , "Empty credentials", Toast.LENGTH_LONG).show();
                }
                else{
                    registerUser(email,password);
                }
            }
        });

    }

    private void registerUser(String email, String password) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this, "Register Successfull" , Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
                else
                    Toast.makeText(RegisterActivity.this , "Regestration Failed" , Toast.LENGTH_SHORT).show();

            }
        });

    }
}

