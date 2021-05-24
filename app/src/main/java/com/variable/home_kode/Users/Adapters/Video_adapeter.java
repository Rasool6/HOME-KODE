package com.variable.home_kode.Users.Adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.util.List;

public class Video_adapeter extends RecyclerView.Adapter<Video_adapeter.ViewHolder> {
    Context context;
    List<AddspostModel> list;

    public Video_adapeter(Context context, List<AddspostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.video_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddspostModel addspostModel=list.get(position);
       holder.videoView.setVideoURI(Uri.parse(addspostModel.videoUri));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);


            videoView = itemView.findViewById(R.id.videoView);


        }

    }
}

