package com.variable.home_kode.Users.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smarteist.autoimageslider.SliderView;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.AdsDetailActivity;
import com.variable.home_kode.Users.Activities.FullScreechImageActivity;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.io.Serializable;
import java.util.List;

public class Photos_adapeter extends RecyclerView.Adapter<Photos_adapeter.ViewHolder> {
    Context context;
    List<String> list;

    public Photos_adapeter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.photoes_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Glide.with(context).load(list.get(position)).into(holder.adsImage);
        holder.adsImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                context.startActivity(new Intent(context, FullScreechImageActivity.class)
                .putExtra("img",list.get(position))
                );
               // ((Activity)context).finish();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView adsImage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            adsImage = itemView.findViewById(R.id.photoValue);


        }

    }
}

