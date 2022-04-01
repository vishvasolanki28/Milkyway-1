package com.example.milkyway;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Space;

import com.example.milkyway.adapter.MyMilkAdapter;
import com.example.milkyway.eventbus.MyUpdateCartEvent;
import com.example.milkyway.listener.ICartLoadListener;
import com.example.milkyway.listener.IMilkLoadListener;
import com.example.milkyway.model.CartModel;
import com.example.milkyway.model.MilkModel;
import com.example.milkyway.utils.SpaceItemDecoration;
import com.example.milkyway.utils.SpaceItemDecoration;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nex3z.notificationbadge.NotificationBadge;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class milk_cart extends AppCompatActivity implements ICartLoadListener, IMilkLoadListener {

    @BindView(R.id.recycler_milk)
    RecyclerView recycler_milk;
    @BindView(R.id.badge)
    NotificationBadge badge;
    @BindView(R.id.mainLayout)
    RelativeLayout mainlayout;
    @BindView(R.id.btnCart)
    FrameLayout btnCart;
    IMilkLoadListener milkLoadListener;
    ICartLoadListener cartLoadListener;

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        if(EventBus.getDefault().hasSubscriberForEvent(MyUpdateCartEvent.class))
            EventBus.getDefault().removeStickyEvent(MyUpdateCartEvent.class);

        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void onUpdateCart(MyUpdateCartEvent event)
    {
        countCartItem();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_milk_cart);



        init();
        loadMilkFromFirebase();
        countCartItem();
    }

    private void loadMilkFromFirebase() {
        List<MilkModel> milkModels =new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Milk").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot milkSnapshot: snapshot.getChildren()){
                        MilkModel milkModel = milkSnapshot.getValue(MilkModel.class);
                        //  assert milkModel != null;
                        milkModel.setKey(milkSnapshot.getKey());
                        milkModels.add(milkModel);
                    }
                    milkLoadListener.onMilkLoadSuccess(milkModels);
                }
                else{
                    milkLoadListener.onMilkLoadFailed("Cannot Find Milk");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                milkLoadListener.onMilkLoadFailed(error.getMessage());
            }
        });
    }

    private void init(){

        ButterKnife.bind(this);

        milkLoadListener = this;
        cartLoadListener = this;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recycler_milk.setLayoutManager(gridLayoutManager);
        recycler_milk.addItemDecoration(new SpaceItemDecoration());

        btnCart.setOnClickListener(view -> startActivity(new Intent(this,CartActivity.class)) );
    }

    @Override
    public void onMilkLoadSuccess(List<MilkModel> milkModelList) {
        MyMilkAdapter adapter = new MyMilkAdapter(this,milkModelList,cartLoadListener);
        recycler_milk.setAdapter(adapter);
    }

    @Override
    public void onMilkLoadFailed(String message) {
        Snackbar.make(mainlayout,message,Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        int cartSum = 0;
        for(CartModel cartModel: cartModelList)
            cartSum += cartModel.getQuantity();
        badge.setNumber(cartSum);
    }

    @Override
    public void onCartLoadFailed(String message) {
        Snackbar.make(mainlayout,message,Snackbar.LENGTH_LONG).show();

    }

    @Override
    protected void onResume() {
        super.onResume();
        countCartItem();
    }

    private void countCartItem() {
        List<CartModel> cartModels = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot cartSnapshot:snapshot.getChildren())
                {
                    CartModel cartModel = cartSnapshot.getValue(CartModel.class);
                    assert cartModel != null;
                    cartModel.setKey(cartSnapshot.getKey());
                    cartModels.add(cartModel);
                }
                cartLoadListener.onCartLoadSuccess(cartModels);
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                cartLoadListener.onCartLoadFailed(error.getMessage());
            }
        });
    }


    public void onback1(View view) {
        startActivity(new Intent(milk_cart.this,CustomerHome.class));
    }
}