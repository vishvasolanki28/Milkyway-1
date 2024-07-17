    package com.example.milkyway;

    import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
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

    public class RetailerHome extends AppCompatActivity {

    BottomNavigationView bottomNavigationView2;
    ImageView additem;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retailer_home);
        SharedPreferences wmbPreference = PreferenceManager.getDefaultSharedPreferences(this);
        boolean isFirstRun = wmbPreference.getBoolean("FIRSTRUN", true);
        SharedPreferences.Editor editor = wmbPreference.edit();

        if (isFirstRun){
            // Code to run once
            DatabaseReference Stocknodes = FirebaseDatabase.getInstance().getReference().child("Stock");
            DatabaseReference StockReal = FirebaseDatabase.getInstance().getReference().child(FirebaseAuth.getInstance().getCurrentUser().getUid());

            Stocknodes.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @com.google.firebase.database.annotations.NotNull DataSnapshot snapshot) {
                    for(DataSnapshot dataSnapshot: snapshot.getChildren())
                    {
                        String key = dataSnapshot.getKey();
                        String name = dataSnapshot.child("name").getValue(String.class);
                        String price = dataSnapshot.child("price").getValue(String.class);
                        String quantity = dataSnapshot.child("quantity").getValue(String.class);
                        String image = dataSnapshot.child("image").getValue(String.class);

                        StockReal.child(key).child("name").setValue(name);
                        StockReal.child(key).child("price").setValue(price);
                        StockReal.child(key).child("quantity").setValue(quantity);
                        StockReal.child(key).child("image").setValue(image);
                    }
                }

                @Override
                public void onCancelled(@NonNull @com.google.firebase.database.annotations.NotNull DatabaseError error) {
                    Toast.makeText(RetailerHome.this, "Stock not created!!!", Toast.LENGTH_SHORT).show();

                }


            });

            editor.putBoolean("FIRSTRUN", false);
            editor.apply();
        }


        bottomNavigationView2=findViewById(R.id.btn1);
        bottomNavigationView2.setSelectedItemId(R.id.dashboard_retailer);
        additem =findViewById(R.id.additem);

        additem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(RetailerHome.this,Additem.class);
                startActivity(intent);

            }
        });
        bottomNavigationView2.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){
                    case R.id.dashboard_retailer:
                        return true;

                    case R.id.orderlist1:
                        Intent intent = new Intent(getApplicationContext(),orderretailer.class);
                        startActivity(intent);
                        finish();
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.profile_ret:

                        Intent intent1 = new Intent(getApplicationContext(),profileretailer.class);
                        startActivity(intent1);
                        finish();
                        overridePendingTransition(0,0);
                        return true;



                }
                return false;
            }
        });

    }
}