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

public class OrderAdapter extends FirebaseRecyclerAdapter <Order, OrderAdapter.OrderViewHolder> {
    //private onItemClick onItemClickListener;
    Context mcon;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */

    public OrderAdapter(Context con, @NonNull FirebaseRecyclerOptions<Order> options) {
        super(options);
        mcon = con;
    }

    @Override
    protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position,@NonNull Order model) {
        holder.orderid.setText(model.getOrderId());
        holder.date.setText(model.getOrderDate());
        holder.payresult.setText(model.getOrderStatus());
        holder.paytype.setText(model.getPaymentMode());
        holder.totpro.setText(String.valueOf(model.getTotalProducts()));
        holder.totalamount.setText(String.valueOf(model.getTotalAmount()));

    }

    @NonNull
    @Override
    public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent,int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_view,parent,false);//Change to order
        return new OrderViewHolder(view);
    }

    class OrderViewHolder extends RecyclerView.ViewHolder {
        TextView orderid,date,payresult,totpro,paytype,totalamount;

        public OrderViewHolder(@NonNull final View itemView) {
            super(itemView);
            //title = itemView.findViewById(R.id.title);
            orderid = itemView.findViewById(R.id.orderid);
            date = itemView.findViewById(R.id.date_time);
            payresult = itemView.findViewById(R.id.payresult);
            totpro = itemView.findViewById(R.id.totpro);
            paytype = itemView.findViewById(R.id.paytype);
            totalamount = itemView.findViewById(R.id.totalamount);

        }
    }
}
