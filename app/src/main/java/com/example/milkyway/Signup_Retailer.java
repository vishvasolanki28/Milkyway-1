package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class Signup_Retailer extends AppCompatActivity {

    private Button back_rsignup,submit_rsignup;
    private EditText email_rsignup,name_rsignup,pass_rsignup,shopname_rsignup,mono_rsignup,conpass_rsignup,city_rsignup,shopadd_rsignup,acc_rsignup,ifsc_rsignup;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference reference;
    String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_retailer);

        mAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("Retailers");


        name_rsignup = findViewById(R.id.name_rsignup);
        email_rsignup = findViewById(R.id.email_rsignup);
        mono_rsignup = findViewById(R.id.mono_rsignup);
        shopadd_rsignup = findViewById(R.id.shopadd_rsignup);
        shopname_rsignup = findViewById(R.id.shopname_rsignup);
        city_rsignup = findViewById(R.id.city_rsignup);
        acc_rsignup = findViewById(R.id.acc_rsignup);
        ifsc_rsignup = findViewById(R.id.ifsc_rsignup);
        pass_rsignup = findViewById(R.id.pass_rsignup);
        conpass_rsignup = findViewById(R.id.conpass_rsignup);
        back_rsignup = findViewById(R.id.back_rsignup);
        submit_rsignup = findViewById(R.id.submit_rsignup);



        back_rsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_Retailer.this,LoginActivity.class);
                startActivity(intent);
            }

        });
        submit_rsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });


    }
    private void registeruser() {

        copyFirebaseData();

        String name = name_rsignup.getText().toString().trim();
        String email = email_rsignup.getText().toString().trim();
        String pass = pass_rsignup.getText().toString().trim();
        String mono = mono_rsignup.getText().toString().trim();
        String acc = acc_rsignup.getText().toString().trim();
        String ifsc = ifsc_rsignup.getText().toString().trim();
        String city = city_rsignup.getText().toString().trim();
        String sname = shopname_rsignup.getText().toString().trim();
        String address = shopadd_rsignup.getText().toString().trim();
        String conpass = conpass_rsignup.getText().toString().trim();

        if (name.isEmpty()) {
            name_rsignup.setError("name is required");
            name_rsignup.requestFocus();

        } else if (email.isEmpty()) {
            email_rsignup.setError("email is required");
            email_rsignup.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            email_rsignup.setError("please provide valid email!!");
            email_rsignup.requestFocus();
        } else if (mono.isEmpty()) {
            mono_rsignup.setError("mobile number is required!!!");
            mono_rsignup.requestFocus();
        } else if (!Patterns.PHONE.matcher(mono).matches()) {
            mono_rsignup.setError("please enter valid number!!!");
            mono_rsignup.requestFocus();
        } else if (sname.isEmpty()) {
            shopname_rsignup.setError("please enter shop name!!");
            shopname_rsignup.requestFocus();
        } else if (address.isEmpty()) {
            shopadd_rsignup.setError("please enter Shop address!!");
            shopadd_rsignup.requestFocus();
        } else if (city.isEmpty()) {
            city_rsignup.setError("Please enter city name");
            city_rsignup.requestFocus();
        } else if (acc.isEmpty()) {
            acc_rsignup.setError("please enter Bank Account number!!");
            acc_rsignup.requestFocus();
        } else if (ifsc.isEmpty()) {
            ifsc_rsignup.setError("please enter IFSC number");
            ifsc_rsignup.requestFocus();
        } else if (pass.isEmpty()) {
            pass_rsignup.setError("password is required!!!");
            pass_rsignup.requestFocus();
        } else if (conpass.isEmpty()) {
            conpass_rsignup.setError("please confrim password!!");
        } else if (!pass.equals(conpass)) {
            Toast.makeText(Signup_Retailer.this, "password is different", Toast.LENGTH_SHORT).show();
        } else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Retailer retailer = new Retailer(name, email, mono, sname, address, city, acc, ifsc, pass);

                        FirebaseDatabase.getInstance().getReference("Retailers").child(uid).setValue(retailer).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup_Retailer.this, "User registered successfully!!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Signup_Retailer.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(Signup_Retailer.this, "Registration failed!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    } else {
                        Toast.makeText(Signup_Retailer.this, "Registration failed!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void copyFirebaseData() {

        //String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference StockNodes = FirebaseDatabase.getInstance().getReference().child("Stock");
        final DatabaseReference StockReal = FirebaseDatabase.getInstance().getReference().child("StockReal").child(uid).child("Stock");

       StockNodes.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot : snapshot.getChildren())
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
           public void onCancelled(@NonNull @NotNull DatabaseError error) {
               Toast.makeText(Signup_Retailer.this, "Stock not created!!!", Toast.LENGTH_SHORT).show();

           }
       });


    }
    }