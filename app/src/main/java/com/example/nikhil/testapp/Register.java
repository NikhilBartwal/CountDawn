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

public class Register extends AppCompatActivity {

    EditText emailin, passin;
    Button registerbtn, reg2loginbtn;
    FirebaseAuth fauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        emailin = findViewById(R.id.emailin);
        passin = findViewById(R.id.passin);
        registerbtn = findViewById(R.id.loginbtn);
        reg2loginbtn = findViewById(R.id.log2regbtn);

        fauth = FirebaseAuth.getInstance();

        // Check if user is already logged in
        if(fauth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        registerbtn.setOnClickListener(new View.OnClickListener() {
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

                // Register using email / pass
                fauth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(Register.this,"Account Created", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),HomeScreen.class));
                            finish();
                        } else
                            Toast.makeText(Register.this,"Error in creating account", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        reg2loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(Register.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
