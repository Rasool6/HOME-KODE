package com.variable.home_kode.Users.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.GpsTracker;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Adapters.Filter_Adapter;
import com.variable.home_kode.Users.Adapters.Filter_ResultAdapter;
import com.variable.home_kode.Users.Adapters.Location_adapeter;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class FilterResultActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Location_adapeter filterAdapter;
    List<AddspostModel> list;
    ProgressBar progressBar;
    TextView resulttxt;
    LinearLayoutManager layoutManager;


    String str_area,str_district,str_propertyType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_result);


        str_district= getIntent().getStringExtra("district");
        str_area= getIntent().getStringExtra("area");
        str_propertyType=getIntent().getStringExtra("propertyType");


        resulttxt=findViewById(R.id.textView28);
        recyclerView=findViewById(R.id.filterResultRecycler);
        progressBar=findViewById(R.id.progressBar);
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        GpsTracker gpsTracker = new GpsTracker(this);
        if (gpsTracker.canGetLocation()) {
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            try {
                // String address=getAddress(latitude,longitude);
                //showLocation.setText(address);
                fetchFromDB(latitude,longitude);

            } catch (RuntimeException e) {
                e.printStackTrace();
            }

        } else {
            gpsTracker.showSettingsAlert();
        }
    //  fetchFromDB();
    }
    //  1.child("edtr_Rent").getValue(String.class)) > max
    //  || Integer.parseInt(snapshot1.child("edtr_Rent").getValue(String.class)) < mini
    private void fetchFromDB(double curlatitude, double curlongitude) {
        progressBar.setVisibility(View.VISIBLE);
        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    progressBar.setVisibility(View.GONE);

                    if (snapshot1.child("spinner_city").getValue(String.class).equals(str_district)
                            || snapshot1.child("area").getValue(String.class).equals(str_area)
                            || snapshot1.child("proType").getValue(String.class).equals(str_propertyType)
                    ) {


                    String address = null;
                    try {
                        double latitude = Double.parseDouble((String) snapshot1.child("latitude").getValue(String.class));
                        double longitude = Double.parseDouble(snapshot1.child("longitud").getValue(String.class));
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
                if (list.size()!=0) {
                    filterAdapter = new Location_adapeter(FilterResultActivity.this, list);
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

                Toast.makeText(FilterResultActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getAddress(double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(FilterResultActivity.this, Locale.getDefault());
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


    public void backFilterResult(View view) {
        startActivity(new Intent(FilterResultActivity.this,User_DashboardpActivity.class));
        finish();
    }
//    @Override
//    public void onBackPressed() {
//        startActivity(new Intent(FilterResultActivity.this,User_DashboardpActivity.class));
//        finish();
//    }
}