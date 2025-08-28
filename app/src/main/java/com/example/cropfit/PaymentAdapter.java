package com.example.cropfit;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ProductViewHolder> {
    Activity mcon;
    List<Product> productList;

    public PaymentAdapter(Activity con, @NonNull List<Product> options) {
// TODO Auto-generated constructor stub
        productList=options;
        mcon=con;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product model = productList.get(position);
        holder.name.setText(model.getName());
        System.out.println("Quantity----"+model.getQuantity());
        String quantity = String.valueOf(model.getQuantity());
        holder.pro_quantity.setText(quantity);
        double amount = model.getQuantity() * Double.parseDouble(model.getPrice());
        holder.price.setText(String.valueOf(amount));
        Picasso.with(mcon).load(model.getIcon()).into(holder.icon);

    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.paymentview_item, parent, false);
        Toast.makeText(mcon,"Redirecting to Payment",Toast.LENGTH_SHORT).show();
        return new ProductViewHolder(view);
    }
    class ProductViewHolder extends RecyclerView.ViewHolder{

        TextView name, price, pro_quantity;
        ImageView icon;
        Button procart;


        public ProductViewHolder(@NonNull final View itemView) {
            super(itemView);
            //title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.product_name);
            pro_quantity = itemView.findViewById(R.id.pro_quantity);
            price = itemView.findViewById(R.id.pro_price);
            icon = itemView.findViewById(R.id.product_icon);
            procart = itemView.findViewById(R.id.pro_cart);

        }

    }

}

