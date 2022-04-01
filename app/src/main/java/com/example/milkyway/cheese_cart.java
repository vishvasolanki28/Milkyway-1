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

import com.example.milkyway.adapter.MyCheeseAdapter;
import com.example.milkyway.adapter.MyMilkAdapter;
import com.example.milkyway.eventbus.MyUpdateCartEvent;
import com.example.milkyway.listener.ICartLoadListener;
import com.example.milkyway.listener.ICheeseLoadListener;
import com.example.milkyway.listener.IMilkLoadListener;
import com.example.milkyway.model.CartModel;
import com.example.milkyway.model.CheeseModel;
import com.example.milkyway.model.MilkModel;
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

public class cheese_cart extends AppCompatActivity implements ICheeseLoadListener, ICartLoadListener {
    @BindView(R.id.recycler_cheese)
    RecyclerView recycler_cheese;
    @BindView(R.id.badge)
    NotificationBadge badge;
    @BindView(R.id.mainLayout)
    RelativeLayout mainlayout;
    @BindView(R.id.btnCart)
    FrameLayout btnCart;
    ICheeseLoadListener cheeseLoadListener;
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
        setContentView(R.layout.activity_cheese_cart);
        
        init();
        loadCheeseFromFirebase();
        countCartItem();
    }

    private void loadCheeseFromFirebase() {
        List<CheeseModel> cheeseModels =new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Cheese").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot cheeseSnapshot: snapshot.getChildren()){
                        CheeseModel cheeseModel = cheeseSnapshot.getValue(CheeseModel.class);
                        //  assert milkModel != null;
                        cheeseModel.setKey(cheeseSnapshot.getKey());
                        cheeseModels.add(cheeseModel);
                    }
                    cheeseLoadListener.onCheeseLoadSuccess(cheeseModels);
                }
                else{
                    cheeseLoadListener.onCheeseLoadFailed("Cannot Find Cheese");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                cheeseLoadListener.onCheeseLoadFailed(error.getMessage());
            }
        });
    }

    private void init(){
        ButterKnife.bind(this);

        cheeseLoadListener = this;
        cartLoadListener = this;
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        recycler_cheese.setLayoutManager(gridLayoutManager);
        recycler_cheese.addItemDecoration(new SpaceItemDecoration());

        btnCart.setOnClickListener(view -> startActivity(new Intent(this,CartActivity.class)) );
    }

    @Override
    public void onCheeseLoadSuccess(List<CheeseModel> cheeseModelList) {
        MyCheeseAdapter adapter = new MyCheeseAdapter(this,cheeseModelList,cartLoadListener);
        recycler_cheese.setAdapter(adapter);
    }

    @Override
    public void onCheeseLoadFailed(String message) {
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
    public void onback4(View view) {
        startActivity(new Intent(cheese_cart.this,CustomerHome.class));
    }
}