package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.milkyway.adapter.MyCartAdapter;
import com.example.milkyway.eventbus.MyUpdateCartEvent;
import com.example.milkyway.listener.ICartLoadListener;
import com.example.milkyway.model.CartModel;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class    CartActivity extends AppCompatActivity implements ICartLoadListener {

    @BindView(R.id.recycler_cart)
    RecyclerView recyclerCart;
    @BindView(R.id.mainLayout)
    RelativeLayout mainLayout;
    @BindView(R.id.btnBack)
    ImageView btnBack;
    @BindView(R.id.txtTotal)
    TextView textTotal;
    private TextView textttl;

    public static final String TEXT_TO_SEND = "com.example.Milkway.TEXT_TO_SEND";
    ICartLoadListener cartLoadListener;
    //public float sum = 0;

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

        loadCartFromFirebase();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        textttl = findViewById(R.id.txtTotal2);
        init();
        loadCartFromFirebase();
    }

    private void loadCartFromFirebase() {
        List<CartModel> cartModels = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Cart").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists())
                {
                    for(DataSnapshot cartSnapshot:snapshot.getChildren())
                    {
                        CartModel cartModel = cartSnapshot.getValue(CartModel.class);
                        //assert cartModel != null;
                        cartModel.setKey(cartSnapshot.getKey());
                        cartModels.add(cartModel);

                    }
                    cartLoadListener.onCartLoadSuccess(cartModels);

                }
                else
                    cartLoadListener.onCartLoadFailed("Cart empty");

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                cartLoadListener.onCartLoadFailed(error.getMessage());

            }
        });
    }

    private void init()
    {
        ButterKnife.bind(this);
        cartLoadListener = this;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerCart.setLayoutManager(layoutManager);
        recyclerCart.addItemDecoration(new DividerItemDecoration(this,layoutManager.getOrientation()));
        btnBack.setOnClickListener(v -> finish());//chsnged

    }

    @Override
    public void onCartLoadSuccess(List<CartModel> cartModelList) {
        double sum =0;
        for(CartModel cartModel : cartModelList)
        {
            sum+=cartModel.getTotalPrice();

        }

        textTotal.setText(String.valueOf(sum));
        MyCartAdapter adapter = new MyCartAdapter(this,cartModelList);
        recyclerCart.setAdapter(adapter);


    }

    @Override
    public void onCartLoadFailed(String message) {
        Snackbar.make(mainLayout,message,Snackbar.LENGTH_LONG).show();

    }

    public void Continue(View view) {

        gotoactivity();

    }

    private void gotoactivity() {
        String summ = textTotal.getText().toString();
        Intent i = new Intent(CartActivity.this,shipping_address.class);
        // Intent intent = new Intent(CartActivity.this,Payment.class);
       // String sum  = String.valueOf(sum);
        i.putExtra("total", summ);
        //startActivity(intent);
        startActivity(i);
        finish();
    }
}