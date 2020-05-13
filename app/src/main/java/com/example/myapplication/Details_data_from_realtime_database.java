package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class Details_data_from_realtime_database extends AppCompatActivity {

    private int position;
    private TextView name, department, mobile_number, blood_group, room_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_data_from_realtime_database);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Bundle bundle = getIntent().getExtras();//For getting position from list

        if(bundle != null)
        {
            position = bundle.getInt("position");
        }

        AulaInfo person = new AulaInfo();
        person = MainActivity.aulaInfoList.get(position);

        name = findViewById(R.id.details_name);
        department = findViewById(R.id.details_department);
        mobile_number = findViewById(R.id.details_mobile_number);
        blood_group = findViewById(R.id.details_blood_group);
        room_number = findViewById(R.id.details_room_number);

        name.setText("Name : "+person.getName());
        department.setText("Dept. : "+person.getDepartment());
        mobile_number.setText("Mobile : "+person.getMobileNumber());
        blood_group.setText("Blood Group : "+person.getBloodGroup());
        room_number.setText("Room number : "+person.getRoomNumber());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
