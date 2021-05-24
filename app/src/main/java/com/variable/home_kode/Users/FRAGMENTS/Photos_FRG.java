package com.variable.home_kode.Users.FRAGMENTS;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.AdsDetailActivity;
import com.variable.home_kode.Users.Adapters.Photos_adapeter;

import java.util.ArrayList;
import java.util.List;


public class Photos_FRG extends Fragment {

    RecyclerView recyclerView;

    Photos_adapeter photos_adapeter;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_photos__f_r_g, container, false);

        recyclerView=view.findViewById(R.id.recyclerViewPhoto);
     //   recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),4));


        fetchImages();

      //  Log.d("data", AdsDetailActivity.addspostModel.imageList);

        photos_adapeter=new Photos_adapeter(getContext(),AdsDetailActivity.addspostModel.imageList);
         recyclerView.setAdapter(photos_adapeter);

        return view;
    }

    private void fetchImages() {

        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });




    }

}