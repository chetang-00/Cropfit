package com.example.cropfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class OrderActivity extends AppCompatActivity {

    private androidx.recyclerview.widget.RecyclerView recyclerView;
    OrderAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //toolbar = findViewById(R.id.toolbar1);
        //cart = findViewById(R.id.cart);
        //headertxt1 = findViewById(R.id.headertxt1);
        recyclerView = findViewById(R.id.rview);

        FirebaseAuth auth = FirebaseAuth.getInstance();

        if (AppConstants.currentUser==null) {
            AppConstants.currentUser= auth.getCurrentUser();
        }
        FirebaseRecyclerOptions<Order>   options = new FirebaseRecyclerOptions.Builder<Order>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Order").orderByChild("emailId").equalTo(AppConstants.currentUser.getEmail()),Order.class)
                .build();

        adapter = new OrderAdapter(OrderActivity.this,options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setToolbar("Shopping");
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.startListening();
    }

//    void setToolbar(String title) {
//        headertxt1.setText(tile);}

}
