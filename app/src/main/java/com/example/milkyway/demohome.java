package com.example.milkyway;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class demohome extends AppCompatActivity {

    private Button btn;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demohome);

        btn = findViewById(R.id.logout);
        editText = findViewById(R.id.quantity);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(demohome.this, "loggedout!!!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(demohome.this,LoginActivity.class));
                finish();
            }
        });




    }

   /* private void addstock(MilkModel milkModel) {
        String quantity =
        DatabaseReference stock = FirebaseDatabase.getInstance().getReference("Stock").child(FirebaseAuth.getInstance().getCurrentUser().getUid());// IN PORJECT ADD UID FOR OTHER USERS
        stock.child(milkModel.getKey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });


    }*/
}