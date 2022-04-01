package com.example.milkyway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Signup_DeliveryBoy extends AppCompatActivity {

    private Button back_dsignup,submit_dsignup;
    private EditText email_dsignup,name_dsignup,pass_dsignup,adhaar_dsignup,mono_dsignup,conpass_dsignup,city_dsignup,add_dsignup,acc_dsignup,ifsc_dsignup;
    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_delivery_boy);

        mAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("DeliveryBoys");

        name_dsignup = findViewById(R.id.name_dsignup);
        email_dsignup = findViewById(R.id.email_dsignup);
        mono_dsignup = findViewById(R.id.mono_dsignup);
        adhaar_dsignup = findViewById(R.id.adhaar_dsignup);
        add_dsignup = findViewById(R.id.add_dsignup);
        city_dsignup = findViewById(R.id.city_dsignup);
        acc_dsignup = findViewById(R.id.acc_dsignup);
        ifsc_dsignup = findViewById(R.id.ifsc_dsignup);
        pass_dsignup = findViewById(R.id.pass_dsignup);
        conpass_dsignup = findViewById(R.id.conpass_dsignup);
        back_dsignup = findViewById(R.id.back_dsignup);
        submit_dsignup = findViewById(R.id.submit_dsignup);


        back_dsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_DeliveryBoy.this,LoginActivity.class);
                startActivity(intent);
            }
        });
        submit_dsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });
    }

    private void registeruser(){
        String name = name_dsignup.getText().toString().trim();
        String email = email_dsignup.getText().toString().trim();
        String pass = pass_dsignup.getText().toString().trim();
        String mono = mono_dsignup.getText().toString().trim();
        String acc = acc_dsignup.getText().toString().trim();
        String ifsc = ifsc_dsignup.getText().toString().trim();
        String city = city_dsignup.getText().toString().trim();
        String adhaar = adhaar_dsignup.getText().toString().trim();
        String address = add_dsignup.getText().toString().trim();
        String conpass = conpass_dsignup.getText().toString().trim();

        if(name.isEmpty()){
            name_dsignup.setError("name is required");
            name_dsignup.requestFocus();

        }else if(email.isEmpty())
        {
            email_dsignup.setError("email is required");
            email_dsignup.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_dsignup.setError("please provide valid email!!");
            email_dsignup.requestFocus();
        }else if(mono.isEmpty())
        {
            mono_dsignup.setError("mobile number is required!!!");
            mono_dsignup.requestFocus();
        }else if(!Patterns.PHONE.matcher(mono).matches())
        {
            mono_dsignup.setError("please enter valid number!!!");
            mono_dsignup.requestFocus();
        }else if(adhaar.isEmpty()){
            adhaar_dsignup.setError("please enter adhaar number!!");
            adhaar_dsignup.requestFocus();
        }else if(address.isEmpty()){
            add_dsignup.setError("please enter address!!");
            add_dsignup.requestFocus();
        }else if(city.isEmpty()){
            city_dsignup.setError("Please enter city name");
            city_dsignup.requestFocus();
        }else if(acc.isEmpty()){
            acc_dsignup.setError("please enter Bank Account number!!");
            acc_dsignup.requestFocus();
        }else if(ifsc.isEmpty()){
            ifsc_dsignup.setError("please enter IFSC number");
            ifsc_dsignup.requestFocus();
        }else if(pass.isEmpty())
        {
            pass_dsignup.setError("password is required!!!");
            pass_dsignup.requestFocus();
        }else if(conpass.isEmpty()){
            conpass_dsignup.setError("please confrim password!!");
        }else if(!pass.equals(conpass)){
            Toast.makeText(this, "password is different", Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Retailer retailer = new Retailer(name, email, mono, adhaar, address, city, acc, ifsc, pass);

                        FirebaseDatabase.getInstance().getReference("DeliveryBoys").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(retailer).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(Signup_DeliveryBoy.this, "User registered successfully!!!", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(Signup_DeliveryBoy.this, LoginActivity.class));
                                    finish();
                                } else {
                                    Toast.makeText(Signup_DeliveryBoy.this, "Registration failed!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Signup_DeliveryBoy.this, "Registration failed!!!" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        }
    }