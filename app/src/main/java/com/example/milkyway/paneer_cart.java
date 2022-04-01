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

import com.example.milkyway.adapter.MyMilkAdapter;
import com.example.milkyway.adapter.MyPaneerAdapter;
import com.example.milkyway.eventbus.MyUpdateCartEvent;
import com.example.milkyway.listener.ICartLoadListener;
import com.example.milkyway.listener.IMilkLoadListener;
import com.example.milkyway.listener.IPaneerLoadListener;
import com.example.milkyway.model.CartModel;
import com.example.milkyway.model.MilkModel;
import com.example.milkyway.model.PaneerModel;
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

public class paneer_cart extends AppCompatActivity implements ICartLoadListener, IPaneerLoadListener {

    @BindView(R.id.recycler_paneer)
    RecyclerView recycler_paneer;
    @BindView(R.id.badge)
    NotificationBadge badge;
    @BindView(R.id.mainLayout)
    RelativeLayout mainlayout;
    @BindView(R.id.btnCart)
    FrameLayout btnCart;
    IPaneerLoadListener paneerLoadListener;
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
        setContentView(R.layout.activity_paneer_cart);

        init();
        loadPaneerFromFirebase();
        countCartItem();
    }

    private void loadPaneerFromFirebase() {
        List<PaneerModel> paneerModels =new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Paneer").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot paneerSnapshot: snapshot.getChildren()){
                        PaneerModel paneerModel = paneerSnapshot.getValue(PaneerModel.class);
                        //  assert milkModel != null;
                        paneerModel.setKey(paneerSnapshot.getKey());
                        paneerModels.add(paneerModel);
                    }
                    paneerLoadListener.onPaneerLoadSuccess(paneerModels);
                }
                else{
                    paneerLoadListener.onPaneerLoadFailed("Cannot Find Paneer");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                paneerLoadListener.onPaneerLoadFailed(error.getMessage());
            }
        });
    }

    private void init(){

        ButterKnife.bind(this);

        paneerLoadListener = this;
        cartLoadListener = this;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recycler_paneer.setLayoutManager(gridLayoutManager);
        recycler_paneer.addItemDecoration(new SpaceItemDecoration());

        btnCart.setOnClickListener(view -> startActivity(new Intent(this,CartActivity.class)) );
    }

    @Override
    public void onPaneerLoadSuccess(List<PaneerModel> paneerModelList) {
        MyPaneerAdapter adapter = new MyPaneerAdapter(this,paneerModelList,cartLoadListener);
        recycler_paneer.setAdapter(adapter);
    }

    @Override
    public void onPaneerLoadFailed(String message) {
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
    public void onback3(View view) {
        startActivity(new Intent(paneer_cart.this,CustomerHome.class));
    }
}