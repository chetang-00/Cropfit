package com.example.cropfit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;

public class ShopActivity extends AppCompatActivity {

    private androidx.recyclerview.widget.RecyclerView recyclerView;
    ShopAdapter adapter;
    EditText search_edit_text;
    String searchTest="";
    ImageView cart;
    //TextView headertxt1;
    //Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        //toolbar = findViewById(R.id.toolbar1);
        //cart = findViewById(R.id.cart);
        //headertxt1 = findViewById(R.id.headertxt1);
        recyclerView = findViewById(R.id.rview);
        cart = findViewById(R.id.cart);
        search_edit_text = findViewById(R.id.search_edit_text);
        search_edit_text.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                searchTest=s.toString();
                adapter.notifyDataSetChanged();
                // you can call or do what you want with your EditText here

                // yourEditText...
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });

        FirebaseRecyclerOptions<Product>  options =null;
        if(searchTest.isEmpty()){
            options = new FirebaseRecyclerOptions.Builder<Product>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Manure"),Product.class)
                    .build();
        }else {
            options = new FirebaseRecyclerOptions.Builder<Product>()
                    .setQuery(FirebaseDatabase.getInstance().getReference().child("Manure"),Product.class)
                    .build();
        }

        adapter = new ShopAdapter(ShopActivity.this,options);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //setToolbar("Shopping");
        recyclerView.setAdapter(adapter);

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ShopActivity.this,CartActivity.class));
            }
        });
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
//        headertxt1.setText(tile);
//    }
}