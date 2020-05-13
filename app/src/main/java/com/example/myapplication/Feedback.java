package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.net.InetAddress;

public class Feedback extends AppCompatActivity {

    private EditText feedback;
    private Button feeder;
    private ProgressBar feedBackProgress;
    private DatabaseReference databaseReference;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mAuth = FirebaseAuth.getInstance();

        databaseReference = FirebaseDatabase.getInstance().getReference("Feedback");

        feedback = findViewById(R.id.feedBackText);
        feeder = findViewById(R.id.feedBackSubmitButton);
        feedBackProgress = findViewById(R.id.feedBackProgressBar);

        feeder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(feedback.getText().toString().isEmpty())
                {
                    feedback.setError("Please enter your feedback");
                    feedback.requestFocus();
                    return;
                }

                if(!isInternetAvailable())
                {
                    Toast.makeText(getApplicationContext(), "Internet connection not avilable", Toast.LENGTH_SHORT).show();
                    return;
                }

                feedbackSending();//For sending feedback to the realtime database

            }

        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean isInternetAvailable() {
        try {
            String command = "ping -c 1 google.com";
            return (Runtime.getRuntime().exec(command).waitFor() == 0);
        } catch (Exception e) {
            return false;
        }
    }

    public void feedbackSending()
    {
        feedBackProgress.setVisibility(View.VISIBLE);

        feeder.setEnabled(false);

        String key = databaseReference.push().getKey();
        String email = mAuth.getCurrentUser().getEmail();

        databaseReference.child(key).setValue("%"+email+"%\n"+feedback.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {



                if(task.isSuccessful())
                {
                    Toast.makeText(getApplicationContext(), "Feedback sent sucessfully", Toast.LENGTH_SHORT).show();

                    finish();

                }

                else
                {
                    feeder.setEnabled(true);

                    feedBackProgress.setVisibility(View.INVISIBLE);

                    Toast.makeText(getApplicationContext(), "Error : "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();

                    feedback.requestFocus();

                    return;
                }
            }

        });
    }

}
