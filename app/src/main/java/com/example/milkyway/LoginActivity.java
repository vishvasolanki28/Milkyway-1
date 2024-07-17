package com.example.milkyway;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText email_login,name,password;
    private Button Loginbtn;
    private Spinner spinner1;
    private TextView forgotTextLink;
    DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        email_login = findViewById(R.id.email_login);
        password = findViewById(R.id.pass_login);
        Loginbtn = findViewById(R.id.Loginbtn);

        spinner1 = findViewById(R.id.spinner2);
        forgotTextLink = findViewById(R.id.forgotpassword);

        databaseReference = FirebaseDatabase.getInstance().getReference("AllUsers");

        Loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });

        List<String> Roles1 = new ArrayList<>();
        Roles1.add(0,"Choose Role");
        Roles1.add("Customer");
        Roles1.add("Retailer");
        Roles1.add("Delivery Boy");

        ArrayAdapter<String> dataadapter1;
        dataadapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,Roles1);

        dataadapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(dataadapter1);



        forgotTextLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText resetMail = new EditText(view.getContext());
                AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
                passwordResetDialog.setTitle("Reset Password?");
                passwordResetDialog.setMessage("Enter Your Email To Recevied Reset Link?");
                passwordResetDialog.setView(resetMail);

                passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //extract email & send mail
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(LoginActivity.this,"Reset Link Sent To Your Email",Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull @NotNull Exception e) {
                                Toast.makeText(LoginActivity.this,"Error ! Reset Link is not Sent. " + e.getMessage(),Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                });
                passwordResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //close the dialog
                    }
                });
                passwordResetDialog.create().show();

            }
        });
    }

    private void login(){
        String email = email_login.getText().toString().trim();
        String pass = password.getText().toString().trim();
        String item = spinner1.getSelectedItem().toString();
        if(email.isEmpty())
        {
            email_login.setError("Email can not be empty");
            email_login.requestFocus();
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches())
        {
            email_login.setError("please provide valid email!!");
            email_login.requestFocus();
        }
        if(pass.isEmpty())
        {
            password.setError("password can not be empty");
            password.requestFocus();
        }
       else{
            mAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful() && item.equals("Customer"))
                    {

                        Toast.makeText(LoginActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,CustomerHome.class);
                        startActivity(intent);
                        finish();
                    }else if(task.isSuccessful() && item.equals("Retailer")){

                        Toast.makeText(LoginActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,RetailerHome.class);
                        startActivity(intent);
                        finish();

                    }else if(task.isSuccessful() && item.equals("Delivery Boy")){

                        Toast.makeText(LoginActivity.this, "Login Successful!!!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this,deliveryboy_dash.class);
                        startActivity(intent);
                        finish();

                    }
                    else{
                        Toast.makeText(LoginActivity.this, "Incorrect email or password", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    public void signup(View view) {
        Intent i =new Intent(LoginActivity.this, Signup_Customer.class);
        startActivity(i);

    }
}