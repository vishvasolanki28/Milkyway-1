package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.milkyway.model.Complaintc;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Complaint extends AppCompatActivity {

    EditText c_orderno,c_email,c_mono,c_address,c_city,c_complaint;
    Button cust_complaint;
    DatabaseReference complaindbref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaint);

        c_orderno=findViewById(R.id.orderno_c);
        c_email=findViewById(R.id.email_c);
        c_mono=findViewById(R.id.mono_c);
        c_address=findViewById(R.id.address_c);
        c_city=findViewById(R.id.city_c);
        c_complaint=findViewById(R.id.text_c);
        cust_complaint=findViewById(R.id.complaint);

        complaindbref= FirebaseDatabase.getInstance().getReference().child("Complaint");

        cust_complaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                complaint();

            }
        });
    }

    private void complaint() {
        String order1,email1,city1,add1,mobile1,complaint1;

        email1=c_email.getText().toString();
        order1=c_orderno.getText().toString();
        mobile1=c_mono.getText().toString();
        add1=c_address.getText().toString();
        city1=c_city.getText().toString();
        complaint1=c_complaint.getText().toString();

        Complaintc complaints=new Complaintc(order1,email1,city1,add1,mobile1,complaint1);
        complaindbref.push().setValue(complaints);
        Toast.makeText(this, "Complaint Filed", Toast.LENGTH_SHORT).show();
        Intent ij =new Intent(Complaint.this,profile.class);
        startActivity(ij);
        finish();

    }

    public void backcomplaint(View view) {
        Intent ij= new Intent(Complaint.this,profile.class);
        startActivity(ij);
        finish();
    }
}