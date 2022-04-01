package com.example.milkyway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class profileretailer extends AppCompatActivity {
    BottomNavigationView bottomNavigationView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileretailer);

        bottomNavigationView2=findViewById(R.id.btn1);
        bottomNavigationView2.setSelectedItemId(R.id.orderlist1);

        bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.dashboard_retailer:
                        startActivity( new Intent(getApplicationContext(),RetailerHome.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.orderlist1:
                        startActivity( new Intent(getApplicationContext(),orderretailer.class));
                        overridePendingTransition(0,0);

                        return true;

                    case R.id.profile_ret:

                        return true;



                }
                return false;
            }
        });
    }
}