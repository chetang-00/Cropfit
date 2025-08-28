package com.example.cropfit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    private LinearLayout maize,paddy,wheat,chilly;

    public HomeFragment() {
        // Required empty public constructor

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home,container,false);
        maize = (LinearLayout)view.findViewById(R.id.maize);
        paddy = (LinearLayout) view.findViewById(R.id.paddy);
        wheat = (LinearLayout) view.findViewById(R.id.wheat);
        chilly = (LinearLayout) view.findViewById(R.id.chilly);

        maize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Maize",Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(getActivity(), DetectionActivity.class);
                startActivity(i1);
            }
        });

        paddy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Paddy",Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(getActivity(),WorkonActivity.class);
                startActivity(i1);
            }
        });

        wheat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Wheat",Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(getActivity(),WorkonActivity.class);
                startActivity(i1);
            }
        });

        chilly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(),"Chilly",Toast.LENGTH_SHORT).show();
                Intent i1 = new Intent(getActivity(),WorkonActivity.class);
                startActivity(i1);
            }
        });

        return view;
    }
}
