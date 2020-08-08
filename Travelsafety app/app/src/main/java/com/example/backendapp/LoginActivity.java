package com.example.backendapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etemail;
    private EditText etpswd;
    private Button btlogin;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        etemail=(EditText)findViewById(R.id.etfrom);
        etpswd= (EditText)findViewById(R.id.etpswd);
        btlogin= (Button)findViewById(R.id.btnlogin);

        auth =FirebaseAuth.getInstance();


        btlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email =etemail.getText().toString();
                final String pswd= etpswd.getText().toString();
                LoginUser(email,pswd);

            }
        });

    }

    private void LoginUser(String email, String pswd) {
        auth.signInWithEmailAndPassword(email, pswd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
            }
        });
        auth.signInWithEmailAndPassword(email, pswd).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "Login Failed. Please Try again", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
