package com.example.cropfit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OfficerActivity extends AppCompatActivity {

    private androidx.recyclerview.widget.RecyclerView recyclerView;
    AppointmentAdapter adapter;
    String district;
    DatabaseReference reff;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_officer);

        district = getIntent().getStringExtra("District");
        System.out.println(district+"District");

        recyclerView = findViewById(R.id.rview);

        FirebaseRecyclerOptions<Appointment> options = new FirebaseRecyclerOptions.Builder<Appointment>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("appointment").child(district), Appointment.class)
                .build();
        System.out.println("list length"+options.getSnapshots().size());

        adapter = new AppointmentAdapter(OfficerActivity.this,options);
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

}