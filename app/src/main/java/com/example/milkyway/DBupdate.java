package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DBupdate extends AppCompatActivity {

    TextInputEditText name,email,mono,address,password,rpassword;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbupdate);
        name = findViewById(R.id.ed1);
        email = findViewById(R.id.ed2);
        mono = findViewById(R.id.ed3);
        address = findViewById(R.id.ed4);
        password = findViewById(R.id.ed5);
        rpassword =findViewById(R.id.ed6);

        reference = FirebaseDatabase.getInstance().getReference("DeliveryBoys");
    }
    public void onback(View view){
        startActivity(new Intent(DBupdate.this,profile_db.class));
    }

    public void updateDB(View view) {
        updateuser();
    }

    private void updateuser() {
        String name1 = name.getText().toString().trim();
        String email1 = email.getText().toString().trim();
        String mono1 = mono.getText().toString().trim();
        String address1 = address.getText().toString().trim();
        String password1 = password.getText().toString().trim();
        String rpassword1 = rpassword.getText().toString().trim();

        if(password1.equals(rpassword1))
        {
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("name").setValue(name1);
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("email").setValue(email1);
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("mono").setValue(mono1);
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("address").setValue(address1);
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("pass").setValue(password1);
            Toast.makeText(this, "Data Updated Sucessfully!!!", Toast.LENGTH_SHORT).show();

        }
        else
        {
            Toast.makeText(this, "Password mismatched!!", Toast.LENGTH_SHORT).show();
        }


    }
}