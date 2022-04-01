package com.example.milkyway;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class CustomerHome extends AppCompatActivity {
    BottomNavigationView bottomNavigationView1;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    LinearLayout l1;
    LinearLayout l2;
    LinearLayout l3;
    LinearLayout l4;
    LinearLayout l5;
    LinearLayout l6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_home);

        l1 = findViewById(R.id.milk);
        l2 = findViewById(R.id.butter);
        l3 = findViewById(R.id.paneer);
        l4 = findViewById(R.id.cheese);
        l5 = findViewById(R.id.dahi);
        l6 = findViewById(R.id.buttermilk);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView=findViewById(R.id.navigationview);

        toolbar=findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.navigation_open,R.string.navigation_close);

        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        bottomNavigationView1=findViewById(R.id.bottom_navigator);
        bottomNavigationView1.setSelectedItemId(R.id.dashboard1);

        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHome.this,milk_cart.class));
            }
        });
        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHome.this,butter_cart.class));
            }
        });
        l3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHome.this,paneer_cart.class));
            }
        });
        l4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHome.this,cheese_cart.class));
            }
        });
        l5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHome.this,dahi_cart.class));
            }
        });
        l6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CustomerHome.this,buttermilk_cart.class));
            }
        });




        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.dashboard1:
                        return true;

                    case R.id.order1:
                        startActivity( new Intent(getApplicationContext(),order.class));
                        overridePendingTransition(0,0);
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


    public void addthis(View view) {
        startActivity(new Intent(CustomerHome.this,Additem.class));
    }
}