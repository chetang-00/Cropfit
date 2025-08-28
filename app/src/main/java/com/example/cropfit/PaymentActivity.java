package com.example.cropfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PaymentActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    PaymentAdapter adapter;
    TextView cartAmount,headertxt1;
    Toolbar toolbar;
    Button payment;
    ImageView cart1,editAddress;
    int totalAmount=0;
    DatabaseReference reff;
    private FirebaseAuth auth;


    private void setProfileData(){
        reff = FirebaseDatabase.getInstance().getReference().child("Profile").child(auth.getCurrentUser().getUid());
        if(reff==null) {
            return;
        }
        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot==null || !dataSnapshot.exists()) {
                    return;
                }
                TextView  nameTV = findViewById(R.id.name);
                TextView  address1TV = findViewById(R.id.address1);
                TextView  address2TV = findViewById(R.id.address2);
                TextView  address3TV = findViewById(R.id.address3);
                TextView  landmarkTV = findViewById(R.id.landmark);
                TextView  cityTV = findViewById(R.id.city);
                TextView  stateTV = findViewById(R.id.state);
                TextView  pincodeTV = findViewById(R.id.pincode);
                TextView  phoneTV = findViewById(R.id.phone);

                String name = dataSnapshot.child("name").getValue().toString();
                String phone = dataSnapshot.child("phone").getValue().toString();
                String address1 = dataSnapshot.child("address1").getValue().toString();
                String address2 = dataSnapshot.child("address2").getValue().toString();
                String address3 = dataSnapshot.child("address3").getValue().toString();
                String landmark = dataSnapshot.child("landmark").getValue().toString();
                String city = dataSnapshot.child("city").getValue().toString();
                String state = dataSnapshot.child("state").getValue().toString();
                String pincode = dataSnapshot.child("pincode").getValue().toString();
                nameTV.setText(name);
                phoneTV.setText(phone);
                address1TV.setText(address1);
                address2TV.setText(address2);
                address3TV.setText(address3);
                landmarkTV.setText(landmark);
                cityTV.setText(city);
                stateTV.setText(state);
                pincodeTV.setText(pincode);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        auth=FirebaseAuth.getInstance();

        setProfileData();
        toolbar = findViewById(R.id.toolbar);
        cartAmount = findViewById(R.id.cartAmount);
        headertxt1 = findViewById(R.id.headertxt1);
        cart1 = findViewById(R.id.cart);
        editAddress = findViewById(R.id.editAddress);
        payment = findViewById(R.id.payment);
        adapter = new PaymentAdapter(PaymentActivity.this,AppConstants.cartList);
        recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        setToolbar("Payment");
        setTotalAmount(true);
        cart1.setVisibility(View.INVISIBLE);

        editAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PaymentActivity.this,ProfileActivity.class);
                startActivity(i);
            }
        });

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i= new Intent(PaymentActivity.this,PurchaseActivity.class);
                i.putExtra("TOTAL_AMOUNT", totalAmount);

                startActivity(i);
            }
        });

//        payment.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(),"Redirecting to Payment Portal",Toast.LENGTH_SHORT).show();
//                Intent i = new Intent(CartActivity.this,PurchaseActivity.class);
//                i.putExtra("cartAmount",totalAmount);
//                startActivity(i);
//            }
//        });

    }

    void setTotalAmount(boolean isCreate){
        totalAmount=0;
        for(Product product : AppConstants.cartList){
            totalAmount+=product.getQuantity() * Double.parseDouble(product.getPrice());
        }
        cartAmount.setText("Rs "+ totalAmount);
        if(!isCreate){
            adapter.notifyDataSetChanged();
        }

    }

    void setToolbar(String title){
        headertxt1.setText(title);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


}