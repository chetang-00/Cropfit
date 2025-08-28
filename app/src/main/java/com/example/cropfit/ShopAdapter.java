package com.example.cropfit;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class ShopAdapter extends FirebaseRecyclerAdapter <Product,ShopAdapter.ProductViewHolder> {
    //private onItemClick onItemClickListener;
    Context mcon;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public ShopAdapter(Context con, @NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
        mcon = con;
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position,@NonNull Product model) {
        holder.name.setText(model.getName());
        holder.price.setText(model.getPrice());
        Picasso.with(mcon).load(model.getIcon()).into(holder.icon);

        // holder.desc.setText(model.getDesc());
        //final String title=model.getTitle();
        final String Key = getRef(position).getKey();
        //Log.d("Clicked","clicked"+title);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("ClickedIt", "it" + model.getName());
                Intent intent = new Intent(mcon,ProductviewActivity.class);
                intent.putExtra("Productdata", model);
                mcon.startActivity(intent);
            }
        });

        holder.procart.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AppConstants.cartList.add(model);
                Toast.makeText(mcon,"Added to Cart Successfully",Toast.LENGTH_SHORT).show();

            }
        });
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.listview_item,parent,false);
        return new ProductViewHolder(view);
    }

    class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView name,price,desc;
        ImageView icon;
        Button procart;

        public ProductViewHolder(@NonNull final View itemView) {
            super(itemView);
            //title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.product_name);
            price = itemView.findViewById(R.id.pro_price);
            icon = itemView.findViewById(R.id.product_icon);
            desc = itemView.findViewById(R.id.product_desc);
            procart = itemView.findViewById(R.id.pro_cart);
        }
    }
}
