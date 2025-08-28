package com.example.cropfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    EditText inputname,inputlandmark,inputaddress1,inputaddress2,inputaddress3,inputcity,inputstate,inputpincode,inputphone;
    Button btnRegister;
    DatabaseReference reff;
    Profile profile;
    private FirebaseAuth auth;


    private void setProfileData(){

        auth=FirebaseAuth.getInstance();

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setProfileData();
        inputname = (EditText) findViewById(R.id.inputname);
        inputlandmark = (EditText) findViewById(R.id.inputlandmark);
        inputaddress1 = (EditText) findViewById(R.id.inputaddress1);
        inputaddress2 = (EditText) findViewById(R.id.inputaddress2);
        inputaddress3 = (EditText) findViewById(R.id.inputaddress3);
        inputcity = (EditText) findViewById(R.id.inputcity);
        inputstate = (EditText) findViewById(R.id.inputstate);
        inputpincode = (EditText) findViewById(R.id.inputpincode);
        inputphone = (EditText) findViewById(R.id.inputphone);
        btnRegister = (Button) findViewById(R.id.btnRegister);
        profile = new Profile();
        reff = FirebaseDatabase.getInstance().getReference().child("Profile");
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(inputpincode.getText()==null || inputpincode.getText().toString() == null || inputpincode.getText().toString().isEmpty()){
                    Toast.makeText(ProfileActivity.this,"Pin code Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(inputaddress1.getText()==null || inputaddress1.getText().toString() == null || inputaddress1.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this,"Door No, House name Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(inputaddress2.getText()==null || inputaddress2.getText().toString() == null || inputaddress2.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this,"Address Lane 1 Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(inputaddress3.getText()==null || inputaddress3.getText().toString() == null || inputaddress3.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this,"Address Lane 2 Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(inputcity.getText()==null || inputcity.getText().toString() == null || inputcity.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this,"District Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(inputlandmark.getText()==null || inputlandmark.getText().toString() == null || inputlandmark.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this,"Landmark Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(inputname.getText()==null || inputname.getText().toString() == null || inputname.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this,"Name Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(inputstate.getText()==null || inputstate.getText().toString() == null || inputstate.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this,"State Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }
                else if(inputphone.getText()==null || inputphone.getText().toString() == null || inputphone.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this,"Phome Number Missing!!",Toast.LENGTH_LONG).show();
                    return;
                }


                int pin = Integer.parseInt(inputpincode.getText().toString().trim());
                Long phn = Long.parseLong(inputphone.getText().toString().trim());
                profile.setName(inputname.getText().toString().trim());
                profile.setLandmark(inputlandmark.getText().toString().trim());
                profile.setAddress1(inputaddress1.getText().toString().trim());
                profile.setAddress2(inputaddress2.getText().toString().trim());
                profile.setAddress3(inputaddress3.getText().toString().trim());
                profile.setCity(inputcity.getText().toString().trim());
                profile.setState(inputstate.getText().toString().trim());
                profile.setPincode(pin);
                profile.setPhone(phn);

                reff.child(auth.getCurrentUser().getUid()).setValue(profile);
                Toast.makeText(ProfileActivity.this,"Saved Successfully",Toast.LENGTH_LONG).show();
                startActivity(new Intent(getApplicationContext(),NavActivity.class));

            }
        });
    }
}