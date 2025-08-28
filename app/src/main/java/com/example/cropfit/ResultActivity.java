package com.example.cropfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    TextView diseasename,symptoms,prevention,pro1,pro2;
    CardView pro1View,pro2View;
    LinearLayout medicine,home;
    DetectionData detectionData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        detectionData= (DetectionData)getIntent().getSerializableExtra("DetectionData");
        diseasename = findViewById(R.id.diseasename);
        symptoms =findViewById(R.id.symptoms);
        prevention =findViewById(R.id.prevention);
        pro1 =findViewById(R.id.pro1);
        pro2 =findViewById(R.id.pro2);
        pro1View =findViewById(R.id.pro1View);
        pro2View =findViewById(R.id.pro2View);
        medicine = (LinearLayout) findViewById(R.id.medicine);
        home = (LinearLayout) findViewById(R.id.homepage);
        diseasename.setText(detectionData.name);
        prevention.setText(detectionData.preventions);
        symptoms.setText(detectionData.symptoms);
        if(detectionData.product1!=null && !detectionData.product1.isEmpty()){
            pro1View.setVisibility(View.VISIBLE);
            pro1.setText(detectionData.product1);
        }else {
            pro1View.setVisibility(View.GONE);
        }
        if(detectionData.product2!=null && !detectionData.product2.isEmpty()){
            pro2View.setVisibility(View.VISIBLE);
            pro2.setText(detectionData.product2);
        }else {
            pro2View.setVisibility(View.GONE);
        }

        medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Shopping Time", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ResultActivity.this, ShopActivity.class));
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Home", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ResultActivity.this, NavActivity.class));
            }
        });

    }
}