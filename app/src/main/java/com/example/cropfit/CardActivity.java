package com.example.cropfit;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CardActivity extends AppCompatActivity {

    int amount1;
    TextView cartAmount2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card);
        cartAmount2 = findViewById(R.id.cartAmount2);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if(extras != null)
            amount1 = extras.getInt("cartAmount1");
        cartAmount2.setText(""+amount1);
    }
}