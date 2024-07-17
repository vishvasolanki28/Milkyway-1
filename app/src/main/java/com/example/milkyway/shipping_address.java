package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.milkyway.model.CartModel;
import com.example.milkyway.model.product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class shipping_address extends AppCompatActivity{

    private Button order,match;
    private TextView name,address,mono,place;
    private EditText name1,address1,mono1;
    private CheckBox check;
    DatabaseReference databaseReference,db1;
    Customer customer;
    String shopreal;
    String coname,coaddress,comono;
    String suid,cuid = "0";


    Spinner spinner;

    ValueEventListener listener,listener1;
    ArrayList<String> list;
    ArrayList<product>  stockitems = new ArrayList<>();
    ArrayList<CartModel> cartitems = new ArrayList<>();
    ArrayList<product>  stockitems1 = new ArrayList<>();
    ArrayList<CartModel> cartitems1 = new ArrayList<>();
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_address);

        check = findViewById(R.id.check);
        name = findViewById(R.id.text1);
        address = findViewById(R.id.text2);
        place = findViewById(R.id.text4);
        mono = findViewById(R.id.text3);
        name1 = findViewById(R.id.edit1);
        address1 = findViewById(R.id.edit2);
        mono1 = findViewById(R.id.edit3);
        match = findViewById(R.id.match);
        //city1 = findViewById(R.id.edit4);
        order = findViewById(R.id.order);
        spinner = (Spinner)findViewById(R.id.spinner4);
        db1 = FirebaseDatabase.getInstance().getReference("Retailers");


        list = new ArrayList<String>();
        list.add(0,"Select Retailer");
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,list);
        spinner.setAdapter(adapter);



        databaseReference = FirebaseDatabase.getInstance().getReference("Customers");


        Intent inntentt = getIntent();
        Bundle b = inntentt.getExtras();
        String textreal = b.getString("total");
        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();




        if(!uid.isEmpty()){
            getCustomerData();
        }
        else
        {
            Toast.makeText(this, "User not found!!", Toast.LENGTH_SHORT).show();
        }



        order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getuid();


                //show(stockitems);
                // matchdata(stockitems,cartitems);
                //Intent i = new Intent(shipping_address.this,Payment.class);
                // i.putExtra("price",textreal);
                //startActivity(i);
                // finish();
            }




        });
        match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                label:
                for(int i=0;i<cartitems1.size();i++){
                    String cname= cartitems1.get(i).getName().toString();
                    for(int j=stockitems1.size()-1;j>=0;j--){

                        String sname=stockitems1.get(j).getName().toString();

                        if(cname.equals(sname)){
                            if(cartitems1.get(i).getQuantity() <= Integer.parseInt(stockitems1.get(j).getQuantity())){
                                Toast.makeText(shipping_address.this, "DONE", Toast.LENGTH_LONG).show();

                                coname = name.getText().toString();
                                coaddress = address.getText().toString();
                                comono = mono.getText().toString();
                                Intent ji= new Intent(shipping_address.this,Payment.class);
                                ji.putExtra("shopreal",shopreal);
                                ji.putExtra("suid",suid);
                                ji.putExtra("cuid",cuid);
                                ji.putExtra("price",textreal);
                                ji.putExtra("coname",coname);
                                ji.putExtra("coaddress",coaddress);
                                ji.putExtra("comono",comono);
                                startActivity(ji);
                                finish();

                            }
                            else
                            {
                                Toast.makeText(shipping_address.this, "Not Sufficient Stock for "+ cname + ", Please Select Other Retailer." , Toast.LENGTH_LONG).show();
                                break label;
                            }

                        }
                    }
                }


            }
        });
        fetchdata();


    }

    private void show(ArrayList<product> stockitems) {
        stockitems1 = stockitems;

    }




    private void getuid() {
        String shopname = spinner.getSelectedItem().toString();
        shopreal=shopname;

        if(shopname.equals("Select Retailer"))
        {
            Toast.makeText(this, "Please Select Retailer", Toast.LENGTH_SHORT).show();
        }
        else {
            DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
            Query query = rootRef.child("Retailers").orderByChild("sname").equalTo(shopname);
            ValueEventListener valueEventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    String key = "0";
                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        key = ds.getKey();
                        suid = key;

                    }
                    FirebaseDatabase.getInstance().getReference(key).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for(DataSnapshot ds: snapshot.getChildren())
                            {
                                product pro = ds.getValue(product.class);
                                stockitems.add(pro);


                            }
                            show(stockitems);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });

                    FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                            for(DataSnapshot ds:snapshot.getChildren())
                            {
                                CartModel cm = ds.getValue(CartModel.class);
                                cartitems.add(cm);
                            }
                            showcart(cartitems);
                        }

                        @Override
                        public void onCancelled(@NonNull @NotNull DatabaseError error) {

                        }
                    });



                }



                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            ((Query) query).addListenerForSingleValueEvent(valueEventListener);
        }
    }

    private void showcart(ArrayList<CartModel> cartitems) {
        cartitems1 = cartitems;
    }




    private void fetchdata() {


        listener=db1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

                String city = place.getText().toString();
                for(DataSnapshot mydata : snapshot.getChildren())
                {
                    Retailer retailer = mydata.getValue(Retailer.class);
                    String str2 = retailer.getSname().toString();
                    String str3 = retailer.getCity().toString();
                    if(str3.equals(city)) {
                        list.add(str2);

                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    private void getCustomerData() {

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid().toString();
        cuid = uid;
        databaseReference.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                customer = snapshot.getValue(Customer.class);
                name.setText(customer.getName());
                mono.setText(customer.getMono());
                address.setText(customer.getAddress());
                place.setText(customer.getCity());


            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
                Toast.makeText(shipping_address.this, "Data Not Found!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void ordernow(View view) {

        if(check.isChecked())
        {
            String s1 = name1.getText().toString();
            String s2 = address1.getText().toString();
            String s3 = mono1.getText().toString();

            Intent i = new Intent(shipping_address.this,Payment.class);
            i.putExtra("name",s1);
            i.putExtra("address",s2);
            i.putExtra("mono",s3);
            startActivity(i);

        }
        else
        {
            String s4 = name.getText().toString();
            String s5 = address.getText().toString();
            String s6 = mono.getText().toString();

            Intent i = new Intent(shipping_address.this,Payment.class);
            i.putExtra("name",s4);
            i.putExtra("address",s5);
            i.putExtra("mono",s6);
            startActivity(i);

        }
    }

    public void backto(View view) {
        Intent i = new Intent(shipping_address.this,CartActivity.class);
        startActivity(i);
        finish();
    }


}