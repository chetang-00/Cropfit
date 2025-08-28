package com.example.cropfit;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class AppointmentAdapter extends FirebaseRecyclerAdapter<Appointment, AppointmentAdapter.AppointmentViewHolder> {
    Context mcon;

    public AppointmentAdapter(Context con, @NonNull FirebaseRecyclerOptions<Appointment> options) {
        super(options);
        mcon = con;
    }

    @Override
    protected void onBindViewHolder(@NonNull AppointmentAdapter.AppointmentViewHolder holder, int position, @NonNull Appointment model) {
        holder.name.setText(model.getName());
        holder.email.setText(model.getEmail());
        holder.place.setText(model.getPlace());
        holder.phone.setText(model.getPhone());
        holder.pcall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone(mcon,model.getPhone());
            }
        });


        // holder.desc.setText(model.getDesc());
        //final String title=model.getTitle();
        final String Key = getRef(position).getKey();
        //Log.d("Clicked","clicked"+title);

    }

    @NonNull
    @Override
    public AppointmentAdapter.AppointmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.appointmentview_item,parent,false);
        return new AppointmentViewHolder(view);
    }

    public static void callPhone(Context activity, String phone) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
        activity.startActivity(intent);

    }


    class AppointmentViewHolder extends RecyclerView.ViewHolder {
        TextView name,email,place,phone;
        ImageView pcall;

        public AppointmentViewHolder(@NonNull final View itemView) {
            super(itemView);
            //title = itemView.findViewById(R.id.title);
            name = itemView.findViewById(R.id.name);
            email = itemView.findViewById(R.id.email);
            phone = itemView.findViewById(R.id.phone);
            place = itemView.findViewById(R.id.place);
            pcall = itemView.findViewById(R.id.pcall);
        }
    }

}
