package com.example.cropfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    CartAdapter adapter;
    TextView cartAmount,headertxt1;
    Toolbar toolbar;
    Button payment;
    ImageView cart1;
    int totalAmount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        toolbar = findViewById(R.id.toolbar);
        cartAmount = findViewById(R.id.cartAmount);
        headertxt1 = findViewById(R.id.headertxt1);
        cart1 = findViewById(R.id.cart);
        payment = findViewById(R.id.payment);
        adapter = new CartAdapter(CartActivity.this,AppConstants.cartList);
        recyclerView = findViewById(R.id.rview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        setToolbar("Cart");
        setTotalAmount(true);
        cart1.setVisibility(View.INVISIBLE);

        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(CartActivity.this,PaymentActivity.class);
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