package com.example.cropfit;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity2 extends AppCompatActivity {

    TextView inputname,inputlandmark,inputaddress1,inputaddress2,inputaddress3,inputcity,inputstate,inputpincode,inputphone;
    DatabaseReference reff;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile2);

        auth=FirebaseAuth.getInstance();

        inputname = (TextView) findViewById(R.id.inputname);
        inputlandmark = (TextView) findViewById(R.id.inputlandmark);
        inputaddress1 = (TextView) findViewById(R.id.inputaddress1);
        inputaddress2 = (TextView) findViewById(R.id.inputaddress2);
        inputaddress3 = (TextView) findViewById(R.id.inputaddress3);
        inputcity = (TextView) findViewById(R.id.inputcity);
        inputstate = (TextView) findViewById(R.id.inputstate);
        inputpincode = (TextView) findViewById(R.id.inputpincode);
        inputphone = (TextView) findViewById(R.id.inputphone);


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

                    String name = dataSnapshot.child("name").getValue().toString();
                    String phone = dataSnapshot.child("phone").getValue().toString();
                    String address1 = dataSnapshot.child("address1").getValue().toString();
                    String address2 = dataSnapshot.child("address2").getValue().toString();
                    String address3 = dataSnapshot.child("address3").getValue().toString();
                    String landmark = dataSnapshot.child("landmark").getValue().toString();
                    String city = dataSnapshot.child("city").getValue().toString();
                    String state = dataSnapshot.child("state").getValue().toString();
                    String pincode = dataSnapshot.child("pincode").getValue().toString();
                    inputname.setText(name);
                    inputphone.setText(phone);
                    inputaddress1.setText(address1);
                    inputaddress2.setText(address2);
                    inputaddress3.setText(address3);
                    inputlandmark.setText(landmark);
                    inputcity.setText(city);
                    inputstate.setText(state);
                    inputpincode.setText(pincode);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
    }
}