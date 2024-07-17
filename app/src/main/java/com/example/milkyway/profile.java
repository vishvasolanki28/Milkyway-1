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

import com.example.milkyway.model.Complaintc;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class profile extends AppCompatActivity {
    BottomNavigationView bottomNavigationView1;
    private TextView name, email, address, mono, name1;
    private Button btn_signout;
    DatabaseReference databaseReference;
    Customer customer;
    String city;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = findViewById(R.id.fullname);
        email = findViewById(R.id.useremail);
        address = findViewById(R.id.address);
        mono = findViewById(R.id.mobileno);
        name1 = findViewById(R.id.retrivedname);
        //auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("Customers");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        if(!uid.isEmpty()){
            getCustomerData();
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
                Toast.makeText(profile.this, "loggedout!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(profile.this, LoginActivity.class));
                finish();
            }
        });


        bottomNavigationView1 = findViewById(R.id.bottom_navigator);
        bottomNavigationView1.setSelectedItemId(R.id.profile1);

        bottomNavigationView1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.dashboard1:
                        startActivity(new Intent(getApplicationContext(), CustomerHome.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.order1:
                        startActivity(new Intent(getApplicationContext(), order.class));
                        overridePendingTransition(0, 0);
                        return true;

                    case R.id.profile1:

                        return true;


                }
                return false;
            }
        });
    }

    private void getCustomerData() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                customer = snapshot.getValue(Customer.class);

                name.setText(customer.getName());
                email.setText(customer.getEmail());
                mono.setText(customer.getMono());
                address.setText(customer.getAddress());
                name1.setText(customer.getName());



            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    public void openupdate(View view) {
        Intent intent = new Intent(profile.this,userupdate.class);
        startActivity(intent);
        finish();

    }

    public void onbackprofile(View view) {
        Intent intent = new Intent(profile.this,CustomerHome.class);
        startActivity(intent);
        finish();
    }

    public void complaint(View view) {
        Intent ij = new Intent(profile.this, Complaint.class);
        startActivity(ij);
        finish();
    }
}