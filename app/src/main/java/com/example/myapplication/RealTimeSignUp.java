package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealTimeSignUp extends AppCompatActivity {

    private EditText userName, mobileNumber, bdayText, roomNumber, location, fatherName, motherName, gurdianPhone;
    private Button bdayButton, submitButton;
    private DatePickerDialog datePickerDialog;
    private Spinner departemntSpinner, bloodGroupSpinner;
    private String[] bgrp;
    private String[] depa;
    private String names, mobileNumbers, departments, bloodGroups, roomNumbers, permanentLocations, fatherNames, mothernames, gurdianPhones, birthdays;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_time_sign_up);
        getSupportActionBar().hide();

        databaseReference = FirebaseDatabase.getInstance().getReference("aula_18_info");

        ViewFinder();//For finding view

        bgrp = getResources().getStringArray(R.array.blood_group);
        depa = getResources().getStringArray(R.array.department);

        spinnerText();//for showing spinner texts

        bdayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dater();//Selecting date by datePicker
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {//For data saving and checking
            @Override
            public void onClick(View v) {
                dataCheckingAndErrorFinding();//For storing, checking and pushing data to database
            }
        });

    }

    private void dataCheckingAndErrorFinding() {

        textgetting();

        if(errorChecking()== -1)
        {
            return;
        }

        AulaInfo aulaInfo = new AulaInfo(names, mobileNumbers, departments, bloodGroups, roomNumbers, permanentLocations, fatherNames, mothernames, gurdianPhones, birthdays);

        try {

            String key = databaseReference.push().getKey();
            databaseReference.child(key).setValue(aulaInfo);

            Toast.makeText(getApplicationContext(), "User registration successful", Toast.LENGTH_SHORT).show();

            Intent intent =new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();

        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }

    }

    private int errorChecking() {

        if(names.isEmpty())
        {
            userName.setError("Please enter your name");
            userName.requestFocus();
            return -1;
        }

        else if(mobileNumbers.length() <11 )
        {
            mobileNumber.setError("Please enter a valid mobile number");
            mobileNumber.requestFocus();
            return -1;
        }

        else if(roomNumbers.isEmpty())
        {
            roomNumber.setError("Please enter your room number");
            roomNumber.requestFocus();
            return -1;
        }

        else if(birthdays.isEmpty())
        {
            bdayText.setError("Please enter your birthday");
            bdayText.requestFocus();
            return -1;
        }

        else if(permanentLocations.isEmpty())
        {
            location.setError("Please enter your permanent location");
            location.requestFocus();
            return -1;
        }

        else if(fatherNames.isEmpty())
        {
            fatherName.setError("Please enter your father's name");
            fatherName.requestFocus();
            return -1;
        }

        else if(mothernames.isEmpty())
        {
            motherName.setError("Please enter your mother's name");
            motherName.requestFocus();
            return -1;
        }

        else if(gurdianPhones.length() < 11)
        {
            gurdianPhone.setError("Please enter a valid phone number");
            gurdianPhone.requestFocus();
            return -1;
        }

        return 0;
    }

    private void textgetting() {
        names = userName.getText().toString();
        mobileNumbers = mobileNumber.getText().toString();
        departments = departemntSpinner.getSelectedItem().toString();
        bloodGroups = bloodGroupSpinner.getSelectedItem().toString();
        roomNumbers = roomNumber.getText().toString();
        permanentLocations = location.getText().toString();
        fatherNames = fatherName.getText().toString();
        mothernames = motherName.getText().toString();
        gurdianPhones = gurdianPhone.getText().toString();
        birthdays = bdayText.getText().toString();
    }

    private void spinnerText() {
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_sample, R.id.textSpinner, bgrp);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, R.layout.spinner_sample, R.id.textSpinner, depa);
        bloodGroupSpinner.setAdapter(adapter1);
        departemntSpinner.setAdapter(adapter2);
    }

    private void dater() {
        DatePicker datePicker = new DatePicker(RealTimeSignUp.this);

        int currentYear = datePicker.getYear();
        int currentMonth = datePicker.getMonth();
        int currentDay = datePicker.getDayOfMonth()+1;

        try {
            datePickerDialog = new DatePickerDialog(RealTimeSignUp.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            birthdays = dayOfMonth+"/"+month+"/"+year;
                            bdayText.setText(birthdays);
                        }
                    }, currentYear, currentMonth, currentDay);

            datePickerDialog.show();
        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void ViewFinder() {
        userName = findViewById(R.id.userName);
        mobileNumber = findViewById(R.id.mobileNumber);
        bdayText = findViewById(R.id.bdayText);
        roomNumber = findViewById(R.id.roomNumber);
        location = findViewById(R.id.permanentAddress);
        fatherName = findViewById(R.id.fathersName);
        motherName = findViewById(R.id.mothersName);
        bdayButton = findViewById(R.id.bdaydatePicker);
        submitButton = findViewById(R.id.submitButton);
        departemntSpinner = findViewById(R.id.deptSpinner);
        bloodGroupSpinner = findViewById(R.id.bloodGroupSpinner);
        gurdianPhone = findViewById(R.id.gurdianPhoneNumber);
    }

}
