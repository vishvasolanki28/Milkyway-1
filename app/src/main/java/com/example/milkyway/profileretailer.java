package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.annotations.NotNull;

public class profileretailer extends AppCompatActivity {
    BottomNavigationView bottomNavigationView2;
    private TextView name, email, address, mono, name1;
    private Button btn_signout;
    DatabaseReference databaseReference;
    Retailer retailer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profileretailer);

        name = findViewById(R.id.fullname);
        email = findViewById(R.id.useremail);
        address = findViewById(R.id.address);
        mono = findViewById(R.id.mobileno);
        name1 = findViewById(R.id.retrivedname);

        databaseReference = FirebaseDatabase.getInstance().getReference("Retailers");

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        if(!uid.isEmpty()){
            getRetailerData();
        }
        else
        {
            Toast.makeText(this, "User not found!!", Toast.LENGTH_SHORT).show();
        }

        btn_signout = findViewById(R.id.btn_signout);
        btn_signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(profileretailer.this, "loggedout!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(profileretailer.this, LoginActivity.class));
                finish();
            }
        });

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

    private void getRetailerData() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                retailer = snapshot.getValue(Retailer.class);
                name.setText(retailer.getName());
                email.setText(retailer.getEmail());
                mono.setText(retailer.getMono());
                address.setText(retailer.getAddress());
                name1.setText(retailer.getName());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }



        });
    }


    public void openupdate(View view) {
        startActivity(new Intent(profileretailer.this, retailerupdate.class));
        finish();

    }
    public void onbackprofile(View view) {
        startActivity(new Intent(profileretailer.this,RetailerHome.class));
        finish();
    }
}