package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class complete_db extends AppCompatActivity {
    BottomNavigationView bottomNavigationView3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_db);

        bottomNavigationView3=findViewById(R.id.btn2);
        bottomNavigationView3.setSelectedItemId(R.id.completed1);

        bottomNavigationView3.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.pending1:
                        startActivity( new Intent(getApplicationContext(),deliveryboy_dash.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.completed1:
                        return true;

                    case R.id.total_order:
                        startActivity( new Intent(getApplicationContext(),total_db.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile_db:
                        startActivity( new Intent(getApplicationContext(),profile_db.class));
                        overridePendingTransition(0,0);
                        return true;


                }
                return false;
            }
        });
    }
}