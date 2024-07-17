package com.example.milkyway;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.milkyway.model.CartModel;
import com.example.milkyway.model.Deemo;
import com.example.milkyway.model.product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

public class Payment extends AppCompatActivity  implements PaymentResultListener {
    //EditText upi,name,email,phone,date;
    EditText date;
    TextView t1;
    Button pay;
    RadioGroup radioGroup;
    RadioButton digital,cashon;
    AlertDialog.Builder builder1;
    int match;
    DatePickerDialog setdate;
    Spinner spnn;
    String date1,shopmobile,customername,name12;
    ArrayList<product>  stockitems = new ArrayList<>();
    ArrayList<CartModel> cartitems = new ArrayList<>();
    ArrayList<product>  stockitems1 = new ArrayList<>();
    ArrayList<CartModel> cartitems1 = new ArrayList<>();
    DatabaseReference rootref;
    DatabaseReference orderref;
   // ArrayList<product> productArrayList;
    //ArrayList<CartModel> cartModelslist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);



        Intent int1 = getIntent();
        Bundle b = int1.getExtras();
       // productArrayList= (ArrayList<product>) b.getSerializable("stockitem");
        String guid = b.getString("suid");
        String price = b.getString("price");
        String srealname= b.getString("shopreal");
        String customeruid = b.getString("cuid");
        customername = b.getString("coname");
        String customeraddress = b.getString("coaddress");
        String customermono = b.getString("comono");
         t1 = findViewById(R.id.text5);


        t1.setText(price);
        //String name=getIntent().getStringExtra("name");
        //String address=getIntent().getStringExtra("address");
        //String mono=getIntent().getStringExtra("mono");

        spnn=findViewById(R.id.spinner1);
        String[] role={"Select Option","Once","Weekly","Monthly"};
        ArrayList<String> rolelist= new ArrayList<>(Arrays.asList(role));
        ArrayAdapter<String> roleadapter= new ArrayAdapter<>(this,R.layout.stylelist,rolelist);
        spnn.setAdapter(roleadapter);


        String name=getIntent().getStringExtra("name");
        String address=getIntent().getStringExtra("address");
        String mono=getIntent().getStringExtra("mono");

        Checkout.preload(getApplicationContext());
        pay=(Button) findViewById(R.id.payament);
        digital=(RadioButton) findViewById(R.id.radio_online);
        cashon=(RadioButton) findViewById(R.id.radio_cash);
        radioGroup=(RadioGroup)findViewById(R.id.radio);
        date=findViewById(R.id.select_date);
        //pay.setText(sname);
        // set text to pay button






        Calendar calendar= Calendar.getInstance();
        final int year=calendar.get(Calendar.YEAR);
        final int month=calendar.get(Calendar.MONTH);
        final int day=calendar.get(Calendar.DAY_OF_MONTH);

        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog=new DatePickerDialog(Payment.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                month=month+1;
                                date1=day+"/"+month+"/"+year;
                                date.setText(date1);

                            }
                        },year,month,day);
                datePickerDialog.show();

            }

        });




        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                match=i;


            }
        });

        pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                Query query = rootRef.child("Retailers").orderByChild("sname").equalTo(srealname);
                ValueEventListener valueEventListener = new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        String key = "0";
                        String mono = "0";
                        for (DataSnapshot ds : dataSnapshot.getChildren()) {
                            key = ds.getKey();
                            Retailer retailer = ds.getValue(Retailer.class);
                            mono = retailer.getMono();
                            shopmobile = mono;


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


                switch (match)
                {
                    case R.id.radio_online:
                        builder1=new AlertDialog.Builder(Payment.this);
                        builder1.setTitle("Payment Confirmation")
                                .setMessage("Continue with Digital Payment")
                                .setCancelable(true)
                                .setPositiveButton("Online", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int k) {

                                        payment();

                                    }
                                })
                                .setNegativeButton("CANCEL",null);
                        builder1.create().show();
                        break;
                    case R.id.radio_cash:
                        builder1=new AlertDialog.Builder(Payment.this);
                        builder1.setTitle("Payment Confirmation")
                                .setMessage("Continue with Cash Payment")
                                .setCancelable(true)
                                .setPositiveButton("CASH", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int k) {
                                        String method = "COD";
                                        String status = "pending";
                                        String Deliver = "No";
                                        label:
                                        for(int i=0;i<cartitems1.size();i++){
                                            String cname= cartitems1.get(i).getName().toString();
                                            for(int j=stockitems1.size()-1;j>=0;j--){

                                                String sname=stockitems1.get(j).getName().toString();

                                                if(cname.equals(sname)){
                                                    if(cartitems1.get(i).getQuantity() <= Integer.parseInt(stockitems1.get(j).getQuantity())){
                                                        int a = cartitems1.get(i).getQuantity();
                                                        int b = Integer.parseInt(stockitems1.get(j).getQuantity());
                                                        int c = b - a;
                                                        String c1=String.valueOf(c);
                                                        String index = String.valueOf(j + 1);

                                                        FirebaseDatabase.getInstance().getReference(guid).child(index).child("quantity").setValue(c1);

                                                      /* DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                                                        Query query = rootRef.child("Retailers").orderByChild("sname").equalTo(srealname);
                                                        ValueEventListener valueEventListener = new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                                String key = "0";
                                                                for (DataSnapshot ds : snapshot.getChildren()) {
                                                                    key = ds.getKey();

                                                                }


                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                                            }
                                                        };*/

                                                        Toast.makeText(Payment.this, "ok" , Toast.LENGTH_LONG).show();

                                                       // Intent cash=new Intent(Payment.this,CustomerHome.class);
                                                       // startActivity(cash);
                                                        //finish();

                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(Payment.this, "Not Sufficient Stock for "+ cname + ", Please Select Other Retailer." , Toast.LENGTH_LONG).show();
                                                        break label;
                                                    }

                                                }
                                            }
                                        }

                                        /*FirebaseDatabase.getInstance().getReference("Customers").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                                                Customer customer = snapshot.getValue(Customer.class);
                                                name12 = customer.getName().toString();
                                                customername = name12;
                                            }

                                            @Override
                                            public void onCancelled(@NonNull @NotNull DatabaseError error) {

                                            }
                                        });*/
                                        String key = "ajfbjsabf";
                                        Deemo demo1 = new Deemo(srealname,guid,price,date1,method,status,shopmobile,customeruid,customername,customeraddress,customermono,Deliver,key,cartitems1);

                                        FirebaseDatabase.getInstance().getReference("Customerorders").child(customeruid).push().setValue(demo1);
                                       /* rootref = FirebaseDatabase.getInstance().getReference();
                                        orderref = rootref.child("CustomerOrders");
                                        String key = orderref.push().getKey();
                                        Deemo demo1 = new Deemo(srealname,guid,price,date1,method,status,shopmobile,customeruid,customername,customeraddress,customermono,Deliver,key,cartitems1);
                                        orderref.child(customeruid).push().setValue(demo1);*/
                                    }
                                })
                                .setNegativeButton("CANCEL",null);
                        builder1.create().show();
                        break;


                }






            }
        });

    }

    public void payment() {
        Checkout checkout = new Checkout();
        checkout.setKeyID("rzp_test_zYcfGqND9EscfM");

        checkout.setImage(R.drawable.logo);

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            options.put("name", "xyz");
            options.put("description", "Reference No. #123456");
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            //options.put("order_id", "order_DBJOWzybf0sJbb");//from response of step 3.
            options.put("theme.color", "#3399cc");
            options.put("currency", "INR");
            options.put("amount", "50000");//pass amount in currency subunits
            options.put("prefill.email", "gaurav.kumar@example.com");
            options.put("prefill.contact","7096784668");
            JSONObject retryObj = new JSONObject();
            retryObj.put("enabled", true);
            retryObj.put("max_count", 4);
            options.put("retry", retryObj);

            checkout.open(activity, options);

        } catch(Exception e) {
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }


    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(Payment.this,"SUCCESSFUL PAYMENT",Toast.LENGTH_SHORT).show();
        Intent j1= new Intent(Payment.this,CustomerHome.class);
        startActivity(j1);
        finish();
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(Payment.this," PAYMENT FAILURE",Toast.LENGTH_SHORT).show();
        Intent j= new Intent(Payment.this,CartActivity.class);
        startActivity(j);
        finish();

    }

    private void show(ArrayList<product> stockitems) {
        stockitems1 = stockitems;

    }
    private void showcart(ArrayList<CartModel> cartitems) {
        cartitems1 = cartitems;
    }



}