package com.example.cropfit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            sleep(3000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
        startActivity(new Intent(this,LoginActivity.class));
        setContentView(R.layout.activity_main);
    }
}