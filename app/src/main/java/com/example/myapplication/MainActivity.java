package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button findByName, findByDept, findByBlood, findByArea;
    private DatabaseReference databaseReference;
    public static List<AulaInfo> aulaInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        databaseReference = FirebaseDatabase.getInstance().getReference("aula_18_info");

        findByName = findViewById(R.id.nameFinder);
        findByDept = findViewById(R.id.deptFinder);
        findByBlood = findViewById(R.id.bloodGroupFinder);
        findByArea = findViewById(R.id.areaFinder);

        findByName.setEnabled(false);//For preventing pre enability of buttons....
        findByDept.setEnabled(false);
        findByBlood.setEnabled(false);
        findByArea.setEnabled(false);

        aulaInfoList = new ArrayList<AulaInfo>();

        getData();

        findByName.setOnClickListener(this);
        findByDept.setOnClickListener(this);
        findByBlood.setOnClickListener(this);
        findByArea.setOnClickListener(this);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(findByName.isEnabled())
        {
            if (item.getItemId() == R.id.feedBackMenu)
            {
                Intent intent = new Intent(getApplicationContext(), Feedback.class);
                startActivity(intent);
            }

            else if(item.getItemId() == R.id.aboutMenu)
            {
                Intent intent = new Intent(getApplicationContext(), AboutApp.class);
                startActivity(intent);
            }

            else
            {
                finish();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                {
                    AulaInfo aulaInfo = dataSnapshot1.getValue(AulaInfo.class);
                    aulaInfoList.add(aulaInfo);
                }
                Toast.makeText(getApplicationContext(), "Data collected sucessfully", Toast.LENGTH_SHORT).show();

                findByName.setEnabled(true);
                findByDept.setEnabled(true);
                findByBlood.setEnabled(true);
                findByArea.setEnabled(true);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Error : "+error, Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.nameFinder)
        {
            Intent intent = new Intent(getApplicationContext(), list_search_name.class);
            startActivity(intent);
        }

        else if(v.getId() == R.id.deptFinder)
        {
            Intent intent = new Intent(getApplicationContext(), list_search_by_dept.class);
            startActivity(intent);
        }

        else if(v.getId() == R.id.bloodGroupFinder)
        {
            Intent intent = new Intent(getApplicationContext(), list_by_blood.class);
            startActivity(intent);
        }

        else
        {
            Intent intent = new Intent(getApplicationContext(), list_by_area.class);
            startActivity(intent);
        }

    }
}
