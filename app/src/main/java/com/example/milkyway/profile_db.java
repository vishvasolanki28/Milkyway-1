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

import org.jetbrains.annotations.NotNull;

public class profile_db extends AppCompatActivity {
    BottomNavigationView bottomNavigationView3;
    private TextView name, email, address, mono, name1;
    private Button btn_signout;
    DatabaseReference databaseReference;
    DeliveryBoy db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_db);

        name = findViewById(R.id.fullname);
        email = findViewById(R.id.useremail);
        address = findViewById(R.id.address);
        mono = findViewById(R.id.mobileno);
        name1 = findViewById(R.id.retrivedname);
        //auth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference("DeliveryBoys");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        if(!uid.isEmpty()){
            getDeliveryBoyData();
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
                Toast.makeText(profile_db.this, "loggedout!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(profile_db.this, LoginActivity.class));
                finish();
            }
        });

        bottomNavigationView3=findViewById(R.id.btn4);
        bottomNavigationView3.setSelectedItemId(R.id.profile_db);

        bottomNavigationView3.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.pending1:
                        startActivity( new Intent(getApplicationContext(),deliveryboy_dash.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.completed1:
                        startActivity( new Intent(getApplicationContext(),complete_db.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.total_order:
                        startActivity( new Intent(getApplicationContext(),total_db.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile_db:

                        return true;


                }
                return false;
            }
        });

    }

    private void getDeliveryBoyData() {
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();

        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                db = snapshot.getValue(DeliveryBoy.class);
                name.setText(db.getName());
                email.setText(db.getEmail());
                mono.setText(db.getMono());
                address.setText(db.getAddress());
                name1.setText(db.getName());

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }


    public void openupdate(View view) {
        Intent intent = new Intent(profile_db.this,DBupdate.class);
        startActivity(intent);
        finish();

    }

    public void onbackprofile(View view) {
        Intent intent = new Intent(profile_db.this,deliveryboy_dash.class);
        startActivity(intent);
        finish();
    }
}