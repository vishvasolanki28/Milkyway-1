package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class order extends AppCompatActivity {
    BottomNavigationView bottomNavigationView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        bottomNavigationView1=findViewById(R.id.bottom_navigator);
        bottomNavigationView1.setSelectedItemId(R.id.order1);

        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.dashboard1:
                        startActivity( new Intent(getApplicationContext(),CustomerHome.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.order1:

                        return true;

                    case R.id.profile1:
                        startActivity( new Intent(getApplicationContext(),profile.class));
                        overridePendingTransition(0,0);
                        return true;



                }
                return false;
            }
        });





















    }
}