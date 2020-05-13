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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class SignUp extends AppCompatActivity {

    private EditText userNameSignUp, passwordSignUp;
    private Button next, signIn;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        getSupportActionBar().hide();

        userNameSignUp = findViewById(R.id.userNameSignUp);
        passwordSignUp = findViewById(R.id.passWordSignUp);
        next = findViewById(R.id.next);
        signIn = findViewById(R.id.signInButton);
        progressBar = findViewById(R.id.progressBar);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), Sign_In_activity.class);
                startActivity(intent);
                finish();
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {

        String email = userNameSignUp.getText().toString().trim();
        String password = passwordSignUp.getText().toString().trim();

        if(email.isEmpty())
        {
            userNameSignUp.setError("Please enter an email address");
            userNameSignUp.requestFocus();
            return;
        }

        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            userNameSignUp.setError("Please enter a valid email address");
            userNameSignUp.requestFocus();
            return;
        }

        else if(password.isEmpty())
        {
            passwordSignUp.setError("Please enter a password");
            passwordSignUp.requestFocus();
            return;
        }

        else if(password.length() < 8)
        {
            passwordSignUp.setError("Please enter a password greater than 8 character");
            passwordSignUp.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(getApplicationContext(), RealTimeSignUp.class);
                            startActivity(intent);
                            finish();
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                            {
                                Toast.makeText(getApplicationContext(), "User is already registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }
}
