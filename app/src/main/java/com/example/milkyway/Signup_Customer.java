package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;

public class Signup_Customer extends AppCompatActivity {

    private Button submit;
    private ImageView back;
    private EditText email_signup,name_signup,password_signup,monumber_signup,conpaasword_signup,city_signup,address_signup;

    private FirebaseAuth mAuth;
    private FirebaseDatabase rootnode;
    private DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_customer);

        mAuth = FirebaseAuth.getInstance();
        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("Customers");

        reference = rootnode.getReference("AllUsers");

        name_signup = findViewById(R.id.name_signup);
        email_signup = findViewById(R.id.email_signup);
        monumber_signup = findViewById(R.id.mono_signup);
        address_signup = findViewById(R.id.address_signup);
        city_signup = findViewById(R.id.city_signup);
        password_signup = findViewById(R.id.setpass_signup);
        conpaasword_signup = findViewById(R.id.conpass_signup);
        back = findViewById(R.id.back);
        submit = findViewById(R.id.submit_signup);

        //backbutton on click
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup_Customer.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        //submmit on click
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registeruser();
            }
        });

    }

    //registeruser method for submit
    private void registeruser(){

        String name = name_signup.getText().toString().trim();
        String email = email_signup.getText().toString().trim();
        String mono = monumber_signup.getText().toString().trim();
        String address = address_signup.getText().toString().trim();
        String city = city_signup.getText().toString().trim();
        String pass = password_signup.getText().toString().trim();
        String conpass = conpaasword_signup.getText().toString().trim();

        boolean isCustomer = true;


        if(name.isEmpty()){
            name_signup.setError("name is required");
            name_signup.requestFocus();

        }else if(email.isEmpty())
        {
            email_signup.setError("email is required");
            email_signup.requestFocus();
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_signup.setError("please provide valid email!!");
            email_signup.requestFocus();
        }else if(mono.isEmpty())
        {
            monumber_signup.setError("mobile number is required!!!");
            monumber_signup.requestFocus();
        }else if(!Patterns.PHONE.matcher(mono).matches())
        {
            monumber_signup.setError("please enter valid number!!!");
            monumber_signup.requestFocus();
        }else if(pass.isEmpty())
        {
            password_signup.setError("password is required!!!");
            password_signup.requestFocus();
        }else if(conpass.isEmpty())
        {
            conpaasword_signup.setError("please confirm password!!!");
            conpaasword_signup.requestFocus();
        }else if(!pass.equals(conpass))
        {
            Toast.makeText(Signup_Customer.this, "password is different", Toast.LENGTH_SHORT).show();
        }
        else
        {
            //creating user in firebase
            mAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull  Task<AuthResult> task) {
                    if(task.isSuccessful())
                    {
                        Customer customer = new Customer(name,email,mono,address,city,pass);

                        AllUsers allUsers = new AllUsers(name,email,pass,isCustomer);

                        FirebaseDatabase.getInstance().getReference("AllUsers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(allUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {

                            }
                        });

                        FirebaseDatabase.getInstance().getReference("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(customer).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    startActivity(new Intent(Signup_Customer.this,LoginActivity.class));
                                    finish();
                                    Toast.makeText(Signup_Customer.this, "customer registered successfully!!!", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(Signup_Customer.this, "Regestration failed!!!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                        FirebaseDatabase.getInstance().getReference("AllUsers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(allUsers).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull @NotNull Task<Void> task) {

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Signup_Customer.this, "Registration failed!!!"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }




    }
}