package com.example.milkyway.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.milkyway.R;
import com.example.milkyway.eventbus.MyUpdateCartEvent;
import com.example.milkyway.listener.ICartLoadListener;
import com.example.milkyway.listener.IrecyclerViewClickListner;
import com.example.milkyway.model.CartModel;
import com.example.milkyway.model.CheeseModel;
import com.example.milkyway.model.MilkModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyCheeseAdapter extends RecyclerView.Adapter<MyCheeseAdapter.MyCheeseViewHolder>{
    private Context context;
    private List<CheeseModel> cheeseModelList;
    private ICartLoadListener iCartLoadListener;

    public MyCheeseAdapter(Context context, List<CheeseModel> cheeseModelList, ICartLoadListener iCartLoadListener) {
        this.context = context;
        this.cheeseModelList = cheeseModelList;
        this.iCartLoadListener = iCartLoadListener;
    }

    @NonNull
    @Override
    public MyCheeseAdapter.MyCheeseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyCheeseAdapter.MyCheeseViewHolder(LayoutInflater.from(context).inflate(R.layout.layout_cheese_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyCheeseAdapter.MyCheeseViewHolder holder, int position) {
        Glide.with(context).load(cheeseModelList.get(position).getImage()).into(holder.imageView);
        holder.txtPrice.setText(new StringBuilder("Rs.").append(cheeseModelList.get(position).getPrice()));
        holder.txtName.setText(new StringBuilder().append(cheeseModelList.get(position).getName()));

        holder.setListner((view, adapterPosition) -> {
            addToCart(cheeseModelList.get(position));
        });
    }

    private void addToCart(CheeseModel cheeseModel) {
        DatabaseReference userCart = FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid());// IN PORJECT ADD UID FOR OTHER USERS
        userCart.child(cheeseModel.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()) //if user already have item in cart
                {
                    //just update quantitu and totalprice
                    CartModel cartModel = snapshot.getValue(CartModel.class);
                    cartModel.setQuantity(cartModel.getQuantity()+1);
                    Map<String,Object> updateData = new HashMap<>();
                    updateData.put("quantity",cartModel.getQuantity());
                    updateData.put("totalPrice",cartModel.getQuantity()*Float.parseFloat(cartModel.getPrice()));

                    userCart.child(cheeseModel.getKey()).updateChildren(updateData).addOnSuccessListener(unused -> {iCartLoadListener.onCartLoadFailed("Add to Cart Success");

                    })
                            .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));
                }
                else//If item not have in cart, add new
                {
                    CartModel cartModel = new CartModel();
                    cartModel.setName(cheeseModel.getName());
                    cartModel.setImage(cheeseModel.getImage());
                    cartModel.setKey(cheeseModel.getKey());
                    cartModel.setPrice(cheeseModel.getPrice());
                    cartModel.setQuantity(1);
                    cartModel.setTotalPrice(Float.parseFloat(cheeseModel.getPrice()));

                    userCart.child(cheeseModel.getKey()).setValue(cartModel).addOnSuccessListener(unused -> {iCartLoadListener.onCartLoadFailed("Add to Cart Success");

                    })
                            .addOnFailureListener(e -> iCartLoadListener.onCartLoadFailed(e.getMessage()));
                }
                EventBus.getDefault().postSticky(new MyUpdateCartEvent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                iCartLoadListener.onCartLoadFailed(error.getMessage());
            }
        });
    }

    @Override
    public int getItemCount() {
        return cheeseModelList.size();
    }



    public class MyCheeseViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.imageView)
        ImageView imageView;
        @BindView(R.id.txtName)
        TextView txtName;
        @BindView(R.id.txtPrice)
        TextView txtPrice;

        IrecyclerViewClickListner listner;

        public void setListner(IrecyclerViewClickListner listner) {
            this.listner = listner;
        }

        public Unbinder unbinder;
        public MyCheeseViewHolder(@NonNull View itemView){
            super(itemView);
            unbinder = ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }
        @Override
        public void onClick(View v) {
            listner.onRecyclerClick(v,getAdapterPosition());
        }
    }
}
