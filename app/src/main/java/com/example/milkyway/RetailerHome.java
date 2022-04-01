package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class RetailerHome extends AppCompatActivity {

    BottomNavigationView bottomNavigationView2;
    ImageView additem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home);

        bottomNavigationView2=findViewById(R.id.btn1);
        bottomNavigationView2.setSelectedItemId(R.id.dashboard_retailer);
        additem =findViewById(R.id.additem);

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                startActivity(new Intent(RetailerHome.this,Additem.class));
            }
        });
        bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.dashboard_retailer:
                        return true;

                    case R.id.orderlist1:
                        startActivity( new Intent(getApplicationContext(),orderretailer.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile_ret:
                        startActivity( new Intent(getApplicationContext(),profileretailer.class));
                        overridePendingTransition(0,0);
                        return true;



                }
                return false;
            }
        });

    }
}