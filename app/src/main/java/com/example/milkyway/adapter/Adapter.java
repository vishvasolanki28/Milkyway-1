package com.example.milkyway.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.milkyway.R;
import com.example.milkyway.model.product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

    Context context;

    private DatabaseReference reference;
    ArrayList<product> products;
    product p1;


    public Adapter(Context context, ArrayList<product> products) {
        this.context = context;
        this.products = products;
    }

    @NonNull
    @NotNull
    @Override
    public Adapter.MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.stockitem,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull Adapter.MyViewHolder holder,final int position) {

        String key = String.valueOf(position + 1);


        product product  = products.get(position);
        holder.name.setText(product.getName());
        holder.price.setText(product.getPrice());
        holder.quantity.setText(product.getQuantity());
        Glide.with(holder.image.getContext()).load(product.getImage()).into(holder.image);
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DialogPlus dialogPlus= DialogPlus.newDialog(holder.image.getContext())
                        .setContentHolder(new ViewHolder(R.layout.updatestock))
                        .setExpanded(true,800).create();

                View updateview=dialogPlus.getHolderView();
                EditText price=updateview.findViewById(R.id.editprice);
                EditText quantity=updateview.findViewById(R.id.editquantity);
                Button updatestock=updateview.findViewById(R.id.btnupadate);

                //getting data of same object
                price.setText(product.getPrice());
                quantity.setText(product.getQuantity());


                dialogPlus.show();

                updatestock.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        String p1 = price.getText().toString().trim();
                        String q1 = quantity.getText().toString().trim();


                        FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .child(key).child("quantity").setValue(q1);
                        dialogPlus.dismiss();



                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView image,edit;
        TextView name,price,quantity;


        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            image = itemView.findViewById(R.id.imageview1);
            edit=(ImageView)itemView.findViewById(R.id.editstock);
        }
    }


}
