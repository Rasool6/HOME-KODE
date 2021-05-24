package com.variable.home_kode.Users.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Adapters.Filter_Adapter;
import com.variable.home_kode.Users.Adapters.Home_adapeter;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static java.security.AccessController.getContext;

public class FilterActivity extends AppCompatActivity {
   RecyclerView recyclerView;
   Filter_Adapter filterAdapter;
   List<AddspostModel> list;
   ProgressBar progressBar;
   TextView resulttxt;

   String str_area,str_district,str_propertyType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);


            str_district= getIntent().getStringExtra("district");
            str_area= getIntent().getStringExtra("area");
            str_propertyType=getIntent().getStringExtra("propertyType");



        resulttxt=findViewById(R.id.textView28);
        recyclerView=findViewById(R.id.filterRecycler);
        progressBar=findViewById(R.id.progressBar);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        fetchFromDB();
    }
    private void fetchFromDB() {
       progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);

                    if (snapshot1.child("spinner_city").getValue(String.class).equals(str_district)
                            || snapshot1.child("area").getValue(String.class).equals(str_area)
                           || snapshot1.child("proType").getValue(String.class).equals(str_propertyType)
                    ) {

                        String address = null;

                        try {
                            double latitude = Double.parseDouble(snapshot1.child("latitude").getValue(String.class));
                            double longitude = Double.parseDouble(snapshot1.child("longitud").getValue(String.class));

                            address = getAddress(latitude, longitude);

                            //  Log.d("TAG", address);
                            //  imgList.add(snapshot1.child("imageList").getValue(String.class));
                            DataSnapshot contentSnapshot = snapshot1.child("/imageList");
                            List<String> listImage = new ArrayList<>();
                            Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                            for (DataSnapshot match : matchSnapShot) {
                                String c = match.getValue(String.class);
                                listImage.add(c);
                                //  Log.d("ddd",listImage.get(0));


                            }
                            list.add(new AddspostModel(
                                    snapshot1.getKey(),
                                    snapshot1.child("user_id").getValue(String.class),
                                    Appdata.UserName,
                                    snapshot1.child("spinner_floor").getValue(String.class),
                                    snapshot1.child("spinner_balcony").getValue(String.class),
                                    snapshot1.child("spinner_bathrooms").getValue(String.class),
                                    snapshot1.child("spinner_rooms").getValue(String.class),
                                    snapshot1.child("houseName").getValue(String.class),
                                    snapshot1.child("str_kitchen").getValue(String.class),
                                    snapshot1.child("str_drawing").getValue(String.class),
                                    snapshot1.child("checkBoxd_drawing").getValue(String.class),
                                    snapshot1.child("str_dinning").getValue(String.class),
                                    snapshot1.child("longitud").getValue(String.class),
                                    snapshot1.child("latitude").getValue(String.class),
                                    snapshot1.child("spinner_month").getValue(String.class),
                                    snapshot1.child("edtr_contactNo").getValue(String.class),
                                    snapshot1.child("str_Sq_Feet").getValue(String.class),
                                    snapshot1.child("str_negotiablefixed").getValue(String.class),
                                    snapshot1.child("edtr_Rent").getValue(String.class),
                                    snapshot1.child("checkBoxd_garage").getValue(String.class),
                                    snapshot1.child("checkBoxd_goddown").getValue(String.class),
                                    snapshot1.child("checkBoxd_office").getValue(String.class),
                                    snapshot1.child("checkBoxd_bachelor").getValue(String.class),
                                    snapshot1.child("checkBoxd_family").getValue(String.class),
                                    snapshot1.child("gas").getValue(String.class),
                                    snapshot1.child("water").getValue(String.class),
                                    snapshot1.child("wifi").getValue(String.class),
                                    snapshot1.child("caretaker").getValue(String.class),
                                    snapshot1.child("securityGuard").getValue(String.class),
                                    snapshot1.child("cctv").getValue(String.class),
                                    snapshot1.child("homeDetail").getValue(String.class),
                                    snapshot1.child("str_checkBox_stall11").getValue(String.class),
                                    snapshot1.child("str_checkBox_sublet11").getValue(String.class),
                                    snapshot1.child("str_fagarge").getValue(String.class),
                                    snapshot1.child("str_lift").getValue(String.class),
                                    snapshot1.child("spinner_city").getValue(String.class),
                                    address,
                                    snapshot1.child("area").getValue(String.class),
                                    snapshot1.child("currentDate").getValue(String.class),
                                    snapshot1.child("yardType").getValue(String.class),
                                    snapshot1.child("proType").getValue(String.class),
                                    snapshot1.child("str_fireAlarm").getValue(String.class),
                                    snapshot1.child("commercial_space").getValue(String.class),
                                    "",
                                    "",
                                    snapshot1.child("videoUri").getValue(String.class),

                                    listImage


                            ));
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                        ;

                        //  Log.d("ddd",listImage.size()+"");
                        //     imageList.add(new SlideModel(snapshot1.child("imageList").getValue(String.class), ScaleTypes.FIT));

                    }
                }
                if (list.size()!=0) {
                    filterAdapter = new Filter_Adapter(FilterActivity.this, list);
                    recyclerView.setAdapter(filterAdapter);
                }
                else {
                    resulttxt.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(FilterActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getAddress(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(FilterActivity.this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
                Log.w("TAG", strReturnedAddress.toString());
            } else {
                Log.w("TAG", "No Address returned!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.w("TAG", "Canont get Address!");
        }
        return strAdd;
    }


//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(FilterActivity.this,User_DashboardpActivity.class));
//        finish();
//    }

    public void backFilter(View view) {
        startActivity(new Intent(FilterActivity.this,User_DashboardpActivity.class));
        finish();
    }
}