package com.example.milkyway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkyway.R;
import com.example.milkyway.model.Deemo;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Orderadapter extends RecyclerView.Adapter<Orderadapter.orderViewHolder> {

    Context context;
    ArrayList<Deemo> orderlist;

    public Orderadapter(Context context, ArrayList<Deemo> orderlist) {
        this.context = context;
        this.orderlist = orderlist;
    }

    @NonNull
    @NotNull
    @Override
    public orderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.customerorderproduct,parent,false);
        return new orderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull orderViewHolder holder, int position) {
        Deemo deemo = orderlist.get(position);
        holder.date.setText(deemo.getDate());
        holder.price.setText(deemo.getPrice());
        holder.shop.setText(deemo.getShop());
        holder.status.setText(deemo.getStatus());
        holder.method.setText(deemo.getMethod());
        holder.rmono.setText(deemo.getRmono());


    }

    @Override
    public int getItemCount() {
        return orderlist.size();
    }


    public static class orderViewHolder extends RecyclerView.ViewHolder{
        TextView date,price,shop,method,status,rmono;

        public orderViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            date =itemView.findViewById(R.id.text2);
            price=itemView.findViewById(R.id.text3);
            shop=itemView.findViewById(R.id.text5);
            rmono=itemView.findViewById(R.id.text6);
            method=itemView.findViewById(R.id.text7);
            status=itemView.findViewById(R.id.text8);
        }
    }
}
