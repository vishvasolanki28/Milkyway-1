package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    final int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {

                Intent Home = new Intent(MainActivity.this , LoginActivity.class);
                startActivity(Home);
                finish();
            }
        }, SPLASH_TIME_OUT);

    }



/*
   @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user!=null)
        {
            startActivity(new Intent(MainActivity.this,CustomerHome.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
        }
        else
        {
            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {

                    Intent Home = new Intent(MainActivity.this , LoginActivity.class);
                    startActivity(Home);
                    finish();
                }
            }, SPLASH_TIME_OUT);
        }
    }*/
}