package com.example.cropfit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class PurchaseActivity extends AppCompatActivity {
    int amount;
    TextView cartAmount1;
    Button credit, debit,pay1,cancel;
    RadioButton net,cod,emi,upi;
    Toolbar toolbar;
    TextView headertxt1;
    ImageView cart1;
    private FirebaseAuth auth;
    final int UPI_PAYMENT = 0;
    int totalAmount=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         totalAmount = getIntent().getIntExtra("TOTAL_AMOUNT",0);

        setContentView(R.layout.activity_purchase);

        auth=FirebaseAuth.getInstance();

        if (AppConstants.currentUser==null) {
            AppConstants.currentUser= auth.getCurrentUser();
        }

        cartAmount1= findViewById(R.id.cartAmount1);
        credit = findViewById(R.id.credit);
        debit = findViewById(R.id.debit);
        net = findViewById(R.id.net);
        cod= findViewById(R.id.cod);
        emi = findViewById(R.id.emi);
        upi = findViewById(R.id.upi);
        pay1 = findViewById(R.id.pay1);
        toolbar = findViewById(R.id.toolbar);
        headertxt1 = findViewById(R.id.headertxt1);
        cancel = findViewById(R.id.cancel);
        setToolbar("Payment Gateway");
        //cart1.setVisibility(View.INVISIBLE);

        cartAmount1.setText(""+totalAmount);


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSuccessOrder("cancelled","Failed");
            }
        });
        credit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PurchaseActivity.this,CardActivity.class);
                i.putExtra("cartAmount1",amount);
                startActivity(i);
            }
        });

        debit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PurchaseActivity.this,CardActivity.class);
                i.putExtra("cartAmount1",amount);
                startActivity(i);
            }
        });

        pay1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(upi.isChecked()){
                    payUsingUpi(String.valueOf(totalAmount),"sample@upi","Cropfit","Payment For Order");//to check amount use 1
//                    onSuccessOrder("success","UPI");
                }else if(cod.isChecked()){
                    onSuccessOrder("success","Cash on Delivery");
                }
            }
        });


    }
    void setToolbar(String title) {
        headertxt1.setText(title);
    }

    void payUsingUpi(String amount, String upiId, String name, String note) {

        Uri uri = Uri.parse("upi://pay").buildUpon()
                .appendQueryParameter("pa", upiId)
                .appendQueryParameter("pn", name)
                .appendQueryParameter("tn", note)
                .appendQueryParameter("am", amount)
                .appendQueryParameter("cu", "INR")
                .build();


        Intent upiPayIntent = new Intent(Intent.ACTION_VIEW);
        upiPayIntent.setData(uri);

        // will always show a dialog to user to choose an app
        Intent chooser = Intent.createChooser(upiPayIntent, "Pay with");

        // check if intent resolves
        if(null != chooser.resolveActivity(getPackageManager())) {
            startActivityForResult(chooser, UPI_PAYMENT);
        } else {
            Toast.makeText(this,"No UPI app found, please install one to continue",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case UPI_PAYMENT:
                if ((RESULT_OK == resultCode) || (resultCode == 11)) {
                    if (data != null) {
                        String trxt = data.getStringExtra("response");
                        //Log.d("UPI", "onActivityResult: " + trxt);
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add(trxt);
                        upiPaymentDataOperation(dataList);
                    } else {
                        //Log.d("UPI", "onActivityResult: " + "Return data is null");
                        ArrayList<String> dataList = new ArrayList<>();
                        dataList.add("nothing");
                        upiPaymentDataOperation(dataList);
                    }
                } else {
                    //Log.d("UPI", "onActivityResult: " + "Return data is null"); //when user simply back without payment
                    ArrayList<String> dataList = new ArrayList<>();
                    dataList.add("nothing");
                    upiPaymentDataOperation(dataList);
                }
                break;
        }
    }

    private void upiPaymentDataOperation(ArrayList<String> data) {
        String str = data.get(0);
        //Log.d("UPIPAY", "upiPaymentDataOperation: "+str);
        String paymentCancel = "";
        if(str == null) str = "discard";
        String status = "";
        String approvalRefNo = "";
        String response[] = str.split("&");
        for (int i = 0; i < response.length; i++) {
            String equalStr[] = response[i].split("=");
            if(equalStr.length >= 2) {
                if (equalStr[0].toLowerCase().equals("Status".toLowerCase())) {
                    status = equalStr[1].toLowerCase();
                }
                else if (equalStr[0].toLowerCase().equals("ApprovalRefNo".toLowerCase()) || equalStr[0].toLowerCase().equals("txnRef".toLowerCase())) {
                    approvalRefNo = equalStr[1];
                }
            }
            else {
                paymentCancel = "Payment cancelled by user.";
            }
        }

        if (status.equals("success")) {
            onSuccessOrder("SUCESS","UPI");
        }
        else if("Payment cancelled by user.".equals(paymentCancel)) {
            Toast.makeText(PurchaseActivity.this, "Payment cancelled by user.", Toast.LENGTH_SHORT).show();
        }
        else {
            Toast.makeText(PurchaseActivity.this, "Transaction failed.Please try again", Toast.LENGTH_SHORT).show();
        }
    }

    void onSuccessOrder(String OrderStatus,String PaymentMode){
        //Code to handle successful transaction here.
        Toast.makeText(PurchaseActivity.this, "Transaction successful.", Toast.LENGTH_SHORT).show();
        // Log.d("UPI", "responseStr: "+approvalRefNo);
        Toast.makeText(this, "YOUR ORDER HAS BEEN PLACED\n THANK YOU AND ORDER AGAIN", Toast.LENGTH_LONG).show();
        Order order = new Order();
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference().child("Order");
        Random r = new Random( System.currentTimeMillis() );
        int randomId= ((1 + r.nextInt(2)) * 10000 + r.nextInt(10000));
        order.setTotalAmount(totalAmount);
        order.setOrderId(String.valueOf(randomId));
        order.setOrderDate(new Date().toString());
        order.setOrderStatus(OrderStatus);
        order.setPaymentMode(PaymentMode);
        order.setTotalProducts(AppConstants.cartList.size());
        if (AppConstants.currentUser!=null) {
            order.setEmailId(AppConstants.currentUser.getEmail());
        }

        reff.child("order"+randomId).setValue(order);
        Toast.makeText(this,"Saved Successfully",Toast.LENGTH_LONG).show();
        startActivity(new Intent(getApplicationContext(),NavActivity.class));
    }
}