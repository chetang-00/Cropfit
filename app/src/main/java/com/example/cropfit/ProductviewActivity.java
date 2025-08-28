package com.example.cropfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ProductviewActivity extends AppCompatActivity {

    Product product;
    TextView productname, productdesc, productprice, number_counter;
    ImageView productimage;
    Button subtract_btn, add_btn,pro_cart;
    ImageView cart;

    private int quantity = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productview);
        product = (Product) getIntent().getSerializableExtra("Productdata");
        quantity = product.getQuantity()==0?1:product.getQuantity();
        product.setQuantity(quantity);
        productname = (TextView) findViewById(R.id.product_name1);
        productdesc = (TextView) findViewById(R.id.product_desc);
        productprice = (TextView) findViewById(R.id.pro_price1);
        productimage = (ImageView) findViewById(R.id.product_icon1);
        number_counter = (TextView) findViewById(R.id.number_counter);
        subtract_btn = (Button) findViewById(R.id.subtract_btn);
        pro_cart = (Button) findViewById(R.id.pro_cart);
        add_btn = (Button) findViewById(R.id.add_btn);
        cart = findViewById(R.id.cart);

        productname.setText(product.getName());
        productprice.setText(product.getPrice());
        productdesc.setText(product.getDesc());
        Picasso.with(this).load(product.getIcon()).into(productimage);
        subtract_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {
                decrement();
            }
        });
        add_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                increment();
            }
        });
        pro_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View mView) {

                AppConstants.cartList.add(product);

                System.out.println( AppConstants.cartList.toString());
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProductviewActivity.this,CartActivity.class));
            }
        });
    }

    public void increment () {
        quantity = quantity + 1;
        display(quantity);
    }

    public void decrement () {
        if (quantity>0){
            quantity = quantity - 1;
            display(quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        number_counter.setText("" + number);
        product.setQuantity(number);


    }


}