package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Sign_In_activity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText emailSignIn, passWordSignIn;
    private Button signInButton, signUpButton;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign__in_activity);
        getSupportActionBar().hide();

        mAuth = FirebaseAuth.getInstance();

        progressBar = findViewById(R.id.progressBar);
        signInButton = findViewById(R.id.signInButton);
        signUpButton = findViewById(R.id.signUpButton);
        emailSignIn = findViewById(R.id.userName);
        passWordSignIn = findViewById(R.id.passWord);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void userLogin() {
        String email = emailSignIn.getText().toString().trim();
        String password = passWordSignIn.getText().toString().trim();

        if(email.isEmpty())
        {
            emailSignIn.setError("Please enter your email address");
            emailSignIn.requestFocus();
            return;
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            emailSignIn.setError("Please enter a valid email address");
            emailSignIn.requestFocus();
            return;
        }

        else if(password.isEmpty())
        {
            passWordSignIn.setError("Please enter a password");
            passWordSignIn.requestFocus();
            return;
        }

        else if(password.length() < 8)
        {
            passWordSignIn.setError("Please enter a password greater than 8 character");
            passWordSignIn.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                progressBar.setVisibility(View.GONE);
                signInButton.setEnabled(false);

                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Sign in successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    signInButton.setEnabled(true);

                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
