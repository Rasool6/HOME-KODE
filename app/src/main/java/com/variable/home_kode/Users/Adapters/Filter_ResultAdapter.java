package com.variable.home_kode.Users.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.AdsDetailActivity;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.io.Serializable;
import java.util.List;

public class Filter_ResultAdapter extends RecyclerView.Adapter<Filter_ResultAdapter.ViewHolder> {
    Context context;
    List<AddspostModel> list;


    public Filter_ResultAdapter(Context context, List<AddspostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.filterresult_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AddspostModel addspostModel=list.get(position);
        holder.propertyType.setText(addspostModel.proType);
        holder.address.setText(addspostModel.address);
        holder.rent.setText(addspostModel.edtr_Rent+"taka"+"."+addspostModel.str_negotiablefixed);
       // holder.distance.setText(addspostModel.distanceInKm+"\tKM away");
        holder.lvingRooms.setText(addspostModel.spinner_rooms);
        Glide.with(context).load(addspostModel.imageList.get(0)).into(holder.imageView);

        holder.detaiBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putSerializable("MYDATA", (Serializable) addspostModel);
                Intent intent=new Intent(context, AdsDetailActivity.class);
                intent.putExtras(bundle);
                context.startActivity(intent);
                ((Activity)context).finish();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        TextView propertyType, distance,address, rent,lvingRooms;
        ImageView imageView;
        ConstraintLayout detaiBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            propertyType = itemView.findViewById(R.id.textView17);
            rent = itemView.findViewById(R.id.textView18);
       //     distance = itemView.findViewById(R.id.textView26);
            lvingRooms = itemView.findViewById(R.id.textView27);
            imageView = itemView.findViewById(R.id.imageView3);
            address = itemView.findViewById(R.id.textView21);
            detaiBtn = itemView.findViewById(R.id.click2);


        }
    }
}

