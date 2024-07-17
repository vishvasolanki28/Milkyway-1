package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkyway.adapter.Orderadapter;
import com.example.milkyway.model.Deemo;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class order extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView1;
    private RecyclerView recyclerView;
    DatabaseReference myref;
    private Orderadapter orderadapter;
    private ArrayList<Deemo> demolist;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);



        recyclerView = findViewById(R.id.recyclerview_cust);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);

        //Firebase
        myref = FirebaseDatabase.getInstance().getReference();

        /*orderadapter = new Orderadapter(this,demolist);
        recyclerView.setAdapter(orderadapter);*/


        //arraylist
        demolist = new ArrayList<>();

        //cleararraylist there was something that not worked
        ClearAll();

       //getdata
        getdata();




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

    private void ClearAll() {
        if(demolist != null)
        {
            demolist.clear();
            if(orderadapter != null)
            {
                orderadapter.notifyDataSetChanged();
            }

        }
        demolist = new ArrayList<>();
    }

    private void getdata() {

        Query query = myref.child("Customerorders").child(getUid());

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                ClearAll();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Deemo deemo = dataSnapshot.getValue(Deemo.class);
                    demolist.add(deemo);
                }

                orderadapter = new Orderadapter(getApplicationContext(),demolist);
                recyclerView.setAdapter(orderadapter);
                orderadapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

    }

    @NotNull
    private String getUid() {
        return FirebaseAuth.getInstance().getCurrentUser().getUid();
    }


}