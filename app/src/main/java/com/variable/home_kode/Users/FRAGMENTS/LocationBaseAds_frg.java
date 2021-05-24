package com.variable.home_kode.Users.FRAGMENTS;

import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
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
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.GpsTracker;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.FilterResultActivity;
import com.variable.home_kode.Users.Adapters.Location_adapeter;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class LocationBaseAds_frg extends Fragment {

    RecyclerView recyclerView;
    Location_adapeter home_adapeter;
    List<AddspostModel> list;
    List<String> imgList;

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
    long mini=0;
    long max=0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_location_base_ads_frg, container, false);
        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_filter_dialog);
        constraintLayout = view.findViewById(R.id.constraintLayout2);
        recyclerView = view.findViewById(R.id.recyclerView2);

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

        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        GpsTracker gpsTracker = new GpsTracker(getContext());
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            try {
                // String address=getAddress(latitude,longitude);
                //showLocation.setText(address);
                fetchFromDB(latitude, longitude);
                lisnterns(latitude, longitude);

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


                        startActivity(new Intent(getContext(), FilterResultActivity.class)
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

/////////////////


        return view;
    }

    private void lisnterns(double curlatitude, double curlongitude) {
        filterResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if ((filter_noOfRooms.getSelectedItem().toString().equals("No of Living Rooms"))
//                        ||(filter_propertyType.getSelectedItem().toString().equals("Property Type")))
//                {
//                    Toast.makeText(getContext(), "Select one filed at least", Toast.LENGTH_SHORT).show();
//                }else{

                fetchFilterroomsData(curlatitude, curlongitude, filter_noOfRooms.getSelectedItem().toString(),
                        filter_propertyType.getSelectedItem().toString()
//                            filter_miniPrice.getSelectedItem().toString(),
//                            filter_maxPrice.getSelectedItem().toString()
                );


                //   }


            }
        });
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        filter_miniPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    fetchFilterMiniData(curlatitude, curlongitude, filter_miniPrice.getSelectedItem().toString());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        filter_maxPrice.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {

                } else {
                    fetchFilterMaxData(curlatitude, curlongitude, filter_maxPrice.getSelectedItem().toString());

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    }


    private void fetchFromDB(double curlatitude, double curlongitude) {
        //   progressDialog.show();
        progressBar.setVisibility(View.VISIBLE);

        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);


                    String address = null;
                    try {
                        double latitude = Double.parseDouble((String) Objects.requireNonNull(snapshot1.child("latitude").getValue(String.class)));
                        double longitude = Double.parseDouble(Objects.requireNonNull(snapshot1.child("longitud").getValue(String.class)));
                        address = getAddress(latitude, longitude);
                        final long rideEstDistance = (long) CalculationByDistance(new LatLng(curlatitude, curlongitude), new LatLng(latitude, longitude));

                        if (rideEstDistance <= 10) {


                            DataSnapshot contentSnapshot = snapshot1.child("/imageList");
                            List<String> listImage = new ArrayList<>();
                            Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                            for (DataSnapshot match : matchSnapShot) {
                                String c = match.getValue(String.class);
                                listImage.add(c);
                                Log.d("ddd", listImage.get(0));
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
                                    snapshot1.child("str_draw_dinn").getValue(String.class),
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
                                    snapshot1.child("str_fagarge11").getValue(String.class),
                                    snapshot1.child("str_lift1").getValue(String.class),
                                    snapshot1.child("spinner_city").getValue(String.class),
                                    address,
                                    snapshot1.child("area").getValue(String.class),
                                    snapshot1.child("currentDate").getValue(String.class),
                                    snapshot1.child("yardType").getValue(String.class),
                                    snapshot1.child("proType").getValue(String.class),
                                    snapshot1.child("str_fireAlarm").getValue(String.class),
                                    snapshot1.child("commercial_space").getValue(String.class),
                                    String.valueOf(rideEstDistance),
                                    "",
                                    snapshot1.child("videoUri").getValue(String.class),

                                    listImage


                            ));

//

                        }
                    } catch (RuntimeException e) {
                        e.printStackTrace();
                    }

                }
                if (list.size() != 0) {
                    recyclerView.setVisibility(View.VISIBLE);
                    resulttxt.setVisibility(View.GONE);

                    home_adapeter = new Location_adapeter(getActivity(), list);
                    recyclerView.setAdapter(home_adapeter);
                    home_adapeter.notifyDataSetChanged();

                } else {
                    progressBar.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.GONE);
                    resulttxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchFilterMaxData(double curlatitude, double curlongitude, String str_max) {
        progressBar.setVisibility(View.VISIBLE);
         max =Long.parseLong(str_max);

        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);
                    if(snapshot1.child("edtr_Rent").exists()) {
                        if (max <= Long.parseLong(Objects.requireNonNull(snapshot1.child("edtr_Rent").getValue(String.class))))
//                            || mini >= Integer.parseInt(snapshot1.child("edtr_Rent").getValue(String.class))
//                             || max<= Integer.parseInt(snapshot1.child("edtr_Rent").getValue(String.class))
                        {


                            String address = null;
                            try {
                                double latitude = Double.parseDouble((String) Objects.requireNonNull(snapshot1.child("latitude").getValue(String.class)));
                                double longitude = Double.parseDouble(Objects.requireNonNull(snapshot1.child("longitud").getValue(String.class)));
                                address = getAddress(latitude, longitude);
                                final long rideEstDistance = (long) CalculationByDistance(new LatLng(curlatitude, curlongitude), new LatLng(latitude, longitude));

                                if (rideEstDistance <= 10) {


                                    DataSnapshot contentSnapshot = snapshot1.child("/imageList");
                                    List<String> listImage = new ArrayList<>();
                                    Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                                    for (DataSnapshot match : matchSnapShot) {
                                        String c = match.getValue(String.class);
                                        listImage.add(c);
                                        Log.d("ddd", listImage.get(0));
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
                                            snapshot1.child("str_draw_dinn").getValue(String.class),
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
                                            snapshot1.child("str_fagarge11").getValue(String.class),
                                            snapshot1.child("str_lift1").getValue(String.class),
                                            snapshot1.child("spinner_city").getValue(String.class),
                                            address,
                                            snapshot1.child("area").getValue(String.class),
                                            snapshot1.child("currentDate").getValue(String.class),
                                            snapshot1.child("yardType").getValue(String.class),
                                            snapshot1.child("proType").getValue(String.class),
                                            snapshot1.child("str_fireAlarm").getValue(String.class),
                                            snapshot1.child("commercial_space").getValue(String.class),
                                            String.valueOf(rideEstDistance),
                                            "",
                                            snapshot1.child("videoUri").getValue(String.class),

                                            listImage


                                    ));

//

                                }
                            } catch (RuntimeException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                }
                if (list.size() != 0) {

                    recyclerView.setVisibility(View.VISIBLE);
                    resulttxt.setVisibility(View.GONE);

                    home_adapeter = new Location_adapeter(getActivity(), list);
                    recyclerView.setAdapter(home_adapeter);
                    home_adapeter.notifyDataSetChanged();

                } else {
                    progressBar.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.GONE);
                    resulttxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //  progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchFilterMiniData(double curlatitude, double curlongitude, String str_mini) {
        progressBar.setVisibility(View.VISIBLE);
         mini = Long.parseLong(str_mini);

        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);

                    if(snapshot1.child("edtr_Rent").exists()) {

                        if (mini >= Long.parseLong(Objects.requireNonNull(snapshot1.child("edtr_Rent").getValue(String.class))))

                        {


                            String address = null;
                            try {
                                double latitude = Double.parseDouble((String) Objects.requireNonNull(snapshot1.child("latitude").getValue(String.class)));
                                double longitude = Double.parseDouble(Objects.requireNonNull(snapshot1.child("longitud").getValue(String.class)));
                                address = getAddress(latitude, longitude);
                                final long rideEstDistance = (long) CalculationByDistance(new LatLng(curlatitude, curlongitude), new LatLng(latitude, longitude));

                                if (rideEstDistance <= 10) {


                                    DataSnapshot contentSnapshot = snapshot1.child("/imageList");
                                    List<String> listImage = new ArrayList<>();
                                    Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                                    for (DataSnapshot match : matchSnapShot) {
                                        String c = match.getValue(String.class);
                                        listImage.add(c);
                                        Log.d("ddd", listImage.get(0));
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
                                            snapshot1.child("str_draw_dinn").getValue(String.class),
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
                                            snapshot1.child("str_fagarge11").getValue(String.class),
                                            snapshot1.child("str_lift1").getValue(String.class),
                                            snapshot1.child("spinner_city").getValue(String.class),
                                            address,
                                            snapshot1.child("area").getValue(String.class),
                                            snapshot1.child("currentDate").getValue(String.class),
                                            snapshot1.child("yardType").getValue(String.class),
                                            snapshot1.child("proType").getValue(String.class),
                                            snapshot1.child("str_fireAlarm").getValue(String.class),
                                            snapshot1.child("commercial_space").getValue(String.class),
                                            String.valueOf(rideEstDistance),
                                            "",
                                            snapshot1.child("videoUri").getValue(String.class),

                                            listImage


                                    ));

//

                                }
                            } catch (RuntimeException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
                if (list.size() != 0) {

                    recyclerView.setVisibility(View.VISIBLE);
                    resulttxt.setVisibility(View.GONE);

                    home_adapeter = new Location_adapeter(getActivity(), list);
                    recyclerView.setAdapter(home_adapeter);
                    home_adapeter.notifyDataSetChanged();

                } else {
                    progressBar.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.GONE);
                    resulttxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //  progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void fetchFilterroomsData(double curlatitude, double curlongitude, String rooms, String propertyType) {
        progressBar.setVisibility(View.VISIBLE);


        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);

                    if (snapshot1.child("spinner_rooms").getValue(String.class).equals(rooms)
//                            || mini >= Integer.parseInt(snapshot1.child("edtr_Rent").getValue(String.class))
//                            || max<= Integer.parseInt(snapshot1.child("edtr_Rent").getValue(String.class))
                            || snapshot1.child("proType").getValue(String.class).equals(propertyType)) {


                        String address = null;
                        try {
                            double latitude = Double.parseDouble((String) Objects.requireNonNull(snapshot1.child("latitude").getValue(String.class)));
                            double longitude = Double.parseDouble(Objects.requireNonNull(snapshot1.child("longitud").getValue(String.class)));
                            address = getAddress(latitude, longitude);
                            final long rideEstDistance = (long) CalculationByDistance(new LatLng(curlatitude, curlongitude), new LatLng(latitude, longitude));

                            if (rideEstDistance <= 10) {


                                DataSnapshot contentSnapshot = snapshot1.child("/imageList");
                                List<String> listImage = new ArrayList<>();
                                Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                                for (DataSnapshot match : matchSnapShot) {
                                    String c = match.getValue(String.class);
                                    listImage.add(c);
                                    Log.d("ddd", listImage.get(0));
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
                                        snapshot1.child("str_draw_dinn").getValue(String.class),
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
                                        snapshot1.child("str_fagarge11").getValue(String.class),
                                        snapshot1.child("str_lift1").getValue(String.class),
                                        snapshot1.child("spinner_city").getValue(String.class),
                                        address,
                                        snapshot1.child("area").getValue(String.class),
                                        snapshot1.child("currentDate").getValue(String.class),
                                        snapshot1.child("yardType").getValue(String.class),
                                        snapshot1.child("proType").getValue(String.class),
                                        snapshot1.child("str_fireAlarm").getValue(String.class),
                                        snapshot1.child("commercial_space").getValue(String.class),
                                        String.valueOf(rideEstDistance),
                                        "",
                                        snapshot1.child("videoUri").getValue(String.class),

                                        listImage


                                ));

//

                            }
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }

                    }
                }
                if (list.size() != 0) {

                    recyclerView.setVisibility(View.VISIBLE);
                    resulttxt.setVisibility(View.GONE);

                    home_adapeter = new Location_adapeter(getActivity(), list);
                    recyclerView.setAdapter(home_adapeter);
                    home_adapeter.notifyDataSetChanged();

                } else {
                    progressBar.setVisibility(View.GONE);

                    recyclerView.setVisibility(View.GONE);
                    resulttxt.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getContext(), "No Result Found", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //  progressDialog.dismiss();
                progressBar.setVisibility(View.GONE);

                Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    String getAddress(double latitude, double longitude) {
        if (Geocoder.isPresent()) {
            Geocoder myLocation = new Geocoder(getContext(), Locale.getDefault());
            List<Address> myList = null;
            try {
                myList = myLocation.getFromLocation(latitude, longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Address address = (Address) myList.get(0);
            String addressStr = "";
            addressStr += address.getAddressLine(0) + ", ";
            addressStr += address.getAddressLine(1) + ", ";
            addressStr += address.getAddressLine(2);
            return address.getAddressLine(0);
        } else {
            return "geoCoder not present";
        }
    }

    public double CalculationByDistance(LatLng StartP, LatLng EndP) {
        int Radius = 6371;// radius of earth in Km
        double lat1 = StartP.latitude;
        double lat2 = EndP.latitude;
        double lon1 = StartP.longitude;
        double lon2 = EndP.longitude;
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);
        //  return Radius * c ;

        return kmInDec;
    }

}