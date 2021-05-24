package com.variable.home_kode.Users.Adapters;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.annotation.Nullable;
import androidx.appcompat.widget.PopupMenu;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.smarteist.autoimageslider.SliderView;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.AdsDetailActivity;
import com.variable.home_kode.Users.Activities.Edit_AdsActivity;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.io.Serializable;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Profile_adapeter extends RecyclerView.Adapter<Profile_adapeter.ViewHolder> {
    Context context;
    List<AddspostModel> list;

    public Profile_adapeter(Context context, List<AddspostModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.profile_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AddspostModel addspostModel=list.get(position);
        holder.flatFor.setText(addspostModel.checkBoxd_family+"/"+addspostModel.checkBoxd_bachelor);
      //  holder.currentDate.setText(addspostModel.currentDate);
        holder.availbleFrom.setText(addspostModel.spinner_month);
        holder.rent.setText("৳"+addspostModel.edtr_Rent+"."+addspostModel.str_negotiablefixed);
        Glide.with(context).load(addspostModel.imageList.get(0)).into(holder.imageView);

//         ((Activity)context).finish();
//        holder.detaiBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//                holder.dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                holder.dialog.show();
//                holder.dialog.setCancelable(true);
//
//                holder.edt_txt = holder.dialog.findViewById(R.id.edit_id);
//                holder.delete_txt = holder.dialog.findViewById(R.id.delete_id);
//                holder.detail_txt = holder.dialog.findViewById(R.id.detail_id);
//
//                holder.edt_txt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
//                holder.delete_txt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                      //  Toast.makeText(context, "delet", Toast.LENGTH_SHORT).show();
//
//                    }
//                });
//                holder.detail_txt.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//
//                        holder.dialog.dismiss();
//
//                    }
//                });
//        holder.dialog.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {
        TextView flatFor, availbleFrom, rent,edt_txt,delete_txt,detail_txt;
        CircleImageView imageView;
        ImageView popPopImage;
        ConstraintLayout detaiBtn;
        ProgressDialog progressDialog;
        Dialog dialog;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            flatFor = itemView.findViewById(R.id.textView17);
            rent = itemView.findViewById(R.id.textView18);
            imageView = itemView.findViewById(R.id.imageView3);
            popPopImage = itemView.findViewById(R.id.imageView4);
            availbleFrom = itemView.findViewById(R.id.textView20);
            detaiBtn = itemView.findViewById(R.id.click2);
            // sliderView=itemView.findViewWithTag(R.id.imageView);


            dialog = new Dialog(context);
            dialog.setContentView(R.layout.custom_dialog);



            popPopImage.setOnClickListener(this);


        }

        @Override
        public void onClick(View v) {

            popUpSHow(v);

        }

        private void popUpSHow(View v) {

            PopupMenu popupMenu= new PopupMenu(v.getContext(),v);
            popupMenu.inflate(R.menu.po_up_menu2);
            popupMenu.setOnMenuItemClickListener(this);
            popupMenu.show();


        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            AddspostModel addspostModel1=list.get(getAdapterPosition());

            switch (item.getItemId()){

                case R.id.item_detail:

                    Bundle bundle=new Bundle();
                    bundle.putSerializable("MYDATA", (Serializable) addspostModel1);
                    Intent intent=new Intent(context, Edit_AdsActivity.class);
                    intent.putExtras(bundle);
                    context.startActivity(intent);

                    return true;
                case R.id.item_delete:

                    //Toast.makeText(context, "Delete", Toast.LENGTH_SHORT).show();
                    AlertDialog.Builder builder=new AlertDialog.Builder(context);
                    builder.setTitle("Delete");
                    builder.setMessage("Are you sure want to delete this Ad");
                    builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            FirebaseDatabase.getInstance().getReference().child("AdsPost").child(addspostModel1.post_id).removeValue(new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError error, @NonNull DatabaseReference ref) {
                                    Toast.makeText(context, "Pray Time Deleted...!", Toast.LENGTH_SHORT).show();

                                }
                            });
                            //
                        }
                    });
                    builder.setNegativeButton("No",null);
                    builder.show();


                    return true;
                default:
                    return false;
            }

        }
    }
}
