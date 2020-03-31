package com.example.nikhil.testapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    Button loginbtn, log2regbtn;
    EditText emailin, passin;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginbtn = findViewById(R.id.loginbtn);
        log2regbtn = findViewById(R.id.log2regbtn);
        emailin = findViewById(R.id.emailin);
        passin = findViewById(R.id.passin);

        fauth = FirebaseAuth.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailin.getText().toString().trim();
                String pass = passin.getText().toString().trim();

                if(email.isEmpty()) {
                    emailin.setError("Enter Email");
                    return;
                }
                if(pass.isEmpty()) {
                    passin.setError("Enter Password");
                    return;
                }
                if(pass.length() < 6) {
                    passin.setError("Password must be > 6 Chars");
                    return;
                }

                // auth the user

                fauth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Login.this,"Login In Successful", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                            finish();
                        } else
                            Toast.makeText(Login.this, "Error: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
        log2regbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
