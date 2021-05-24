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
import com.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.smarteist.autoimageslider.SliderView;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.AdsDetailActivity;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Home_adapeter extends RecyclerView.Adapter<Home_adapeter.ViewHolder> {
    Context context;
    List<AddspostModel> list;


    public Home_adapeter(Context context, List<AddspostModel> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.home_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        if (position%3==0 && position!=0){
            AdLoader.Builder builder = new AdLoader.Builder(
                    context, "ca-app-pub-3940256099942544/2247696110");

            builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                @Override
                public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                    holder.templateView.setNativeAd(unifiedNativeAd);
                }
            });

            final AdLoader adLoader = builder.build();
            adLoader.loadAd(new AdRequest.Builder().build());
            // holder.shayariTxt.setText(shayariData.get(position).getSharayi());

            holder.templateView.setVisibility(View.VISIBLE);
        }

        AddspostModel addspostModel=list.get(position);
        holder.poster_name.setText(addspostModel.posterName);
        holder.currentDate.setText(addspostModel.currentDate);
        holder.address.setText(addspostModel.address);
        holder.flatFortxt.setText(addspostModel.checkBoxd_family+"/"+addspostModel.checkBoxd_bachelor+"/"+addspostModel.checkBoxd_office+"/"+addspostModel.str_checkBox_stall11);
        holder.ciytAreatxt.setText(addspostModel.spinner_city+"/"+addspostModel.area);
        holder.rent.setText("৳"+addspostModel.edtr_Rent+"."+addspostModel.str_negotiablefixed);

        holder.avail_month.setText(addspostModel.spinner_month);
        Glide.with(context).load(addspostModel.userImageUri).into(holder.profile_img);
        Glide.with(context).load(addspostModel.imageList.get(0)).into(holder.adsImage);
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
//         native ads works..//


//////////////
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView poster_name, currentDate, address, rent, avail_month, ciytAreatxt,flatFortxt;
        ImageView adsImage, popPopImage;
        CircleImageView profile_img;
        ConstraintLayout detaiBtn;
        SliderView sliderView;
        TemplateView templateView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            templateView = itemView.findViewById(R.id.my_template);


            profile_img = itemView.findViewById(R.id.imageView8);
            poster_name = itemView.findViewById(R.id.textView2);
            currentDate = itemView.findViewById(R.id.textView4);
            address = itemView.findViewById(R.id.textView5);

            adsImage    = itemView.findViewById(R.id.imageView);
            rent        = itemView.findViewById(R.id.textView10);
            avail_month = itemView.findViewById(R.id.textView25);
            ciytAreatxt = itemView.findViewById(R.id.textView6);
            flatFortxt = itemView.findViewById(R.id.textView8);

            detaiBtn = itemView.findViewById(R.id.click);
            // sliderView=itemView.findViewWithTag(R.id.imageView);


        }

    }
}

