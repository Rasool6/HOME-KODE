package com.variable.home_kode.Users.FRAGMENTS;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.variable.home_kode.GpsTracker;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.FilterActivity;
import com.variable.home_kode.Users.Adapters.Home_adapeter;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class Home_frg extends Fragment {

    RecyclerView recyclerView;
    Home_adapeter home_adapeter;
    List<AddspostModel> list;
    List<String> imgList;
    SearchView searchViewtxt;
    ImageView imageView6, filterBtn;
    //ProgressDialog progressDialog;
    TextView resulttxt;
    Dialog dialog;
    Spinner district, area, houseType;
    Spinner filter_noOfRooms, filter_maxPrice, filter_miniPrice;
    SearchableSpinner filter_propertyType;
    MaterialButton searchBtn, cancelBtn, filterResultBtn;
    ProgressBar progressBar;
    LinearLayoutManager layoutManager;
    ConstraintLayout constraintLayout;
    String user_Name, imgUrl;
    boolean isScroll = false;
    int currentItem, scrollItem, totalItems;
    private Parcelable recyclerViewState;
    long mini=0;
    long max=0;
    String city;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_frg, container, false);
        //  progressDialog=new ProgressDialog(getContext());
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_filter_dialog);
        recyclerView = view.findViewById(R.id.recyclerView1);
        constraintLayout = view.findViewById(R.id.constraintLayout2);

        filterBtn = view.findViewById(R.id.imageView6);
        filterResultBtn = view.findViewById(R.id.button4);
        resulttxt = view.findViewById(R.id.textView29);
        progressBar = view.findViewById(R.id.progressBar2);

        filter_noOfRooms = view.findViewById(R.id.spinner);
        filter_miniPrice = view.findViewById(R.id.textInputLayout2);
        filter_maxPrice = view.findViewById(R.id.textInputLayout3);
        filter_propertyType = view.findViewById(R.id.spinner2);

        list = new ArrayList<>();
        imgList = new ArrayList<>();
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged(recyclerView, newState);
//                if (newState== AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
//                    isScroll=true;
//                }
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                currentItem=layoutManager.getChildCount();
//                totalItems=layoutManager.getChildCount();
//                scrollItem=layoutManager.findFirstVisibleItemPosition();
//                if (isScroll && currentItem+scrollItem==totalItems){
//                    isScroll=false;
//                   fetchFromDB();
//
//                }
//            }
//        });
        GpsTracker gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            try {
               String str_city1 =getCurrentAddress(latitude,longitude);
//                showLocation.setText(address);
                fetchFromDB(str_city1);


            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        } else {
            gpsTracker.showSettingsAlert();
        }


        filterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.show();
                dialog.setCancelable(true);
                cancelBtn = dialog.findViewById(R.id.button2);
                searchBtn = dialog.findViewById(R.id.button3);
                district = dialog.findViewById(R.id._dynamic);
                area = dialog.findViewById(R.id.spinner3);
                houseType = dialog.findViewById(R.id.spinner4);

                searchBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        startActivity(new Intent(getContext(), FilterActivity.class)
                                .putExtra("district", district.getSelectedItem().toString())
                                .putExtra("area", area.getSelectedItem().toString())
                                .putExtra("propertyType", houseType.getSelectedItem().toString())
                        );


                        // dialog.dismiss();

                    }
                });
                cancelBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        dialog.dismiss();

                    }
                });
            }
        });
        //  fetchProfiledata();

        filterResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                 if (     (filter_noOfRooms.getSelectedItem().toString().equals("No of Living Rooms"))
//                         ||(filter_propertyType.getSelectedItem().toString().equals("Property Type"))
//                         ||(filter_miniPrice.getSelectedItem().toString().equals("Select Minimum Price"))
//                         ||(filter_maxPrice.getSelectedItem().toString().equals("Select Maximum Price")))
//                 {
//                     Toast.makeText(getContext(), "Select one filed at least", Toast.LENGTH_SHORT).show();
//                 }else{

                fetchFilterroomsData(
                        filter_noOfRooms.getSelectedItem().toString(),
                        filter_propertyType.getSelectedItem().toString()
//                             filter_miniPrice.getSelectedItem().toString(),
//                             filter_maxPrice.getSelectedItem().toString()
                );
                //  }


            }
        });
        filter_maxPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                   fetchFilterMaxData(filter_maxPrice.getSelectedItem().toString());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        filter_miniPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    fetchFilterMINIData(filter_miniPrice.getSelectedItem().toString());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // filterListenser();
        return view;
    }

    //String propertyType,String str_mini,String str_max
    private void fetchFilterMINIData(String str_mini) {
        progressBar.setVisibility(View.VISIBLE);

         mini = Long.parseLong(str_mini);

//        int max= Integer.parseInt(str_max);
        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);


        try {

           if (snapshot1.child("edtr_Rent").exists())
           {
                    if (mini >= Long.parseLong(Objects.requireNonNull(snapshot1.child("edtr_Rent").getValue(String.class)))) {


                        try {
                            String address = null;
                            double latitude = Double.parseDouble(Objects.requireNonNull(snapshot1.child("latitude").getValue(String.class)));
                            double longitude = Double.parseDouble(Objects.requireNonNull(snapshot1.child("longitud").getValue(String.class)));

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
                                    snapshot1.child("posterName").getValue(String.class),
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
                                    snapshot1.child("checkBoxRent_garage").getValue(String.class),
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
                                    snapshot1.child("userImageUri").getValue(String.class),
                                    snapshot1.child("videoUri").getValue(String.class),
                                    listImage


                            ));
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }

                    }

//
       }

        }catch(NumberFormatException ex)
        {
              Log.d("TAG",ex.getMessage());
        }
                }
                if (list.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    resulttxt.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.GONE);
                    home_adapeter = new Home_adapeter(getActivity(), list);
                    home_adapeter.notifyDataSetChanged();
                    recyclerView.setAdapter(home_adapeter);


                } else {
                    recyclerView.setVisibility(View.GONE);
                    resulttxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.VISIBLE);

                    //Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void fetchFilterMaxData(String str_max) {
        progressBar.setVisibility(View.VISIBLE);

//        int mini= Integer.parseInt(str_mini);
         max = Long.parseLong(str_max);
        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);


                if(snapshot1.child("edtr_Rent").exists()) {

                    if (max <= Long.parseLong(Objects.requireNonNull(snapshot1.child("edtr_Rent").getValue(String.class)))) {


                        String address = null;

                        try {
                            double latitude = Long.parseLong(Objects.requireNonNull(snapshot1.child("latitude").getValue(String.class)));
                            double longitude = Double.parseDouble(Objects.requireNonNull(snapshot1.child("longitud").getValue(String.class)));

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
                                    snapshot1.child("posterName").getValue(String.class),
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
                                    snapshot1.child("checkBoxRent_garage").getValue(String.class),
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
                                    snapshot1.child("userImageUri").getValue(String.class),
                                    snapshot1.child("videoUri").getValue(String.class),
                                    listImage


                            ));
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }


                    }
                }
//


                }
                if (list.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    resulttxt.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.GONE);
                    home_adapeter = new Home_adapeter(getActivity(), list);
                    home_adapeter.notifyDataSetChanged();
                    recyclerView.setAdapter(home_adapeter);


                } else {
                    recyclerView.setVisibility(View.GONE);
                    resulttxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.VISIBLE);

                    //Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void fetchFilterroomsData(String rooms, String propertyType) {
        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);


                //   if (snapshot1.child("spinner_city").getValue(String.class).equals(city)) {
                       if (
                               snapshot1.child("spinner_rooms").getValue(String.class).equals(rooms)
                                       || snapshot1.child("proType").getValue(String.class).equals(propertyType)
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
                                       snapshot1.child("posterName").getValue(String.class),
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
                                       snapshot1.child("checkBoxRent_garage").getValue(String.class),
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
                                       snapshot1.child("userImageUri").getValue(String.class),
                                       snapshot1.child("videoUri").getValue(String.class),
                                       listImage


                               ));
                           } catch (RuntimeException e) {
                               e.printStackTrace();
                           }


                       }

                   }

              //  }
                if (list.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    resulttxt.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.GONE);

                    home_adapeter = new Home_adapeter(getActivity(), list);
                    home_adapeter.notifyDataSetChanged();
                    recyclerView.setAdapter(home_adapeter);


                } else {
                    recyclerView.setVisibility(View.GONE);
                    resulttxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    constraintLayout.setVisibility(View.VISIBLE);

                    //Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }


    private void fetchFromDB(String str_city1) {
        //progressDialog.show();
//        progressBar.setVisibility(View.VISIBLE);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//
//                for (int i = 0; i < 2; i++) {
        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);

                    if (snapshot1.child("spinner_city").exists()) {
                        if (snapshot1.child("spinner_city").getValue(String.class).equals(str_city1)) {


                            DataSnapshot contentSnapshot = snapshot1.child("/imageList");
                            List<String> listImage = new ArrayList<>();
                            Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                            for (DataSnapshot match : matchSnapShot) {
                                String c = match.getValue(String.class);
                                listImage.add(c);
                                //  Log.d("ddd",listImage.get(0));
                            }


                            String address = null;
                            try {
                                double latitude = Double.parseDouble(snapshot1.child("latitude").getValue(String.class));
                                double longitude = Double.parseDouble(snapshot1.child("longitud").getValue(String.class));


                                address = getAddress(latitude, longitude);

                                list.add(new AddspostModel(
                                        snapshot1.getKey(),
                                        snapshot1.child("user_id").getValue(String.class),
                                        snapshot1.child("posterName").getValue(String.class),
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
                                        snapshot1.child("checkBoxRent_garage").getValue(String.class),
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
                                        snapshot1.child("userImageUri").getValue(String.class),
                                        snapshot1.child("videoUri").getValue(String.class),
                                        listImage


                                ));
                            } catch (RuntimeException e) {
                                e.printStackTrace();
                            }
                            ;


                        }
                    }
                }

                if (list.size() != 0) {
//                               progressBar.setVisibility(View.VISIBLE);
//
//                                new Handler().postDelayed(new Runnable() {
//                                    @Override
//                                    public void run() {
//
//                                        for (int i = 0; i < 2; i++) {

                    recyclerView.setVisibility(View.VISIBLE);
                    resulttxt.setVisibility(View.GONE);
                    progressBar.setVisibility(View.GONE);
                    home_adapeter = new Home_adapeter(getActivity(), list);
                    home_adapeter.notifyDataSetChanged();
                    recyclerView.setAdapter(home_adapeter);


//                                        } }
//                                },5000);


                } else {
                    recyclerView.setVisibility(View.GONE);
                    resulttxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);

                    //Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
//                    home_adapeter.notifyDataSetChanged();
//                    progressBar.setVisibility(View.GONE);


    }


    private String getCurrentAddress(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder((Activity) getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                city = returnedAddress.getLocality();
             //  Toast.makeText(getContext(), returnedAddress.getLocality(), Toast.LENGTH_SHORT).show();
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
        return city;
       // return strAdd;
    }
    private String getAddress(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder((Activity) getContext(), Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
           // city = returnedAddress.getLocality();
            //   Toast.makeText(getContext(), returnedAddress.getLocality(), Toast.LENGTH_SHORT).show();
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


}