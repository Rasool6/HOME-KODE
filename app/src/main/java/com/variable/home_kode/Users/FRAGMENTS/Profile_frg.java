package com.variable.home_kode.Users.FRAGMENTS;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.login.LoginManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.ChangePasswordeActivity;
import com.variable.home_kode.Users.Activities.EditProfileActivity;
import com.variable.home_kode.Users.Activities.UserLoginActivity;
import com.variable.home_kode.Users.Adapters.Profile_adapeter;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;


public class Profile_frg extends Fragment {

    RecyclerView recyclerView;
    Profile_adapeter profile_adapter;
    List<AddspostModel> list;
    List<String> imgList;
    TextView nameTxt,city_txt;
    CircleImageView imageView;
    ImageView sittingBtn;
    Dialog dialog;
    ProgressDialog progressDialog;
    TextView edt_txt,delete_txt,detail_txt;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
//  Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    ConstraintLayout uperHaed;
    String user_phone;
    String user_id;
    String loginStatus;
    LinearLayoutManager layoutManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_profile_frg, container, false);
        progressDialog=new ProgressDialog(getContext());
        recyclerView=view.findViewById(R.id.profileRecylcer);
        nameTxt=view.findViewById(R.id.textView20);
        sittingBtn=view.findViewById(R.id.imageView5);
        imageView=view.findViewById(R.id.circleImageView);
        drawerLayout=view.findViewById(R.id.drawer);
        navigationView=view.findViewById(R.id.navigation_view);


        city_txt=view.findViewById(R.id.textView21);
        layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);
        list=new ArrayList<>();
        imgList=new ArrayList<>();

        dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.custom_dialog);


        sittingBtn.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("WrongConstant")
            @Override
            public void onClick(View v) {
//                DrawerLayout navDrawer = findViewById(R.id.drawer_layout);
                drawerLayout.openDrawer(Gravity.END);

            }
        });

            fetchProfiledata();


   navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
       @Override
       public boolean onNavigationItemSelected(@NonNull MenuItem item) {
           switch (item.getItemId()) {
               case R.id.editProfId:
                   if (loginStatus.equals("1")){
                   startActivity(new Intent(getContext(), EditProfileActivity.class)
                           .putExtra("name",nameTxt.getText().toString())
                       .putExtra("user_phone", user_phone)
                   );
                       getActivity().finish();
                   }else {
                       Toast.makeText(getContext(), "Social login user cannot change profile data here...! ", Toast.LENGTH_SHORT).show();
                   }

                   break;
                   case R.id.changePasssId:
                       if (loginStatus.equals("1")){

                           startActivity(new Intent(getContext(), ChangePasswordeActivity.class));
                           getActivity().finish();
                       }else {
                           Toast.makeText(getContext(), "Social login user cannot change profile data here...! ", Toast.LENGTH_SHORT).show();
                       }
                       break;
                   case R.id.logout:
                       SharedPreferences sharedPreferences=getActivity().getSharedPreferences(Appdata.PREFS_NAME, Context.MODE_PRIVATE);
                       SharedPreferences.Editor editor=sharedPreferences.edit();
                       editor.clear();
                       editor.apply();
                       GoogleSignIn.getClient(
                               getContext(),
                               new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
                       ).signOut();

                       FirebaseAuth.getInstance().signOut();
                       LoginManager.getInstance().logOut();

                       startActivity(new Intent(getContext(), UserLoginActivity.class));
                         getActivity().finish();

                       break;
           }
           return false;
       }
   });


        fetchMyPOSTS();
       // fetchProfileData();

        return view;
    }

    private void fetchProfiledata() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    if (snapshot1.getKey().equals(Appdata.UID)) {

                        nameTxt.setText(snapshot1.child("user_Name").getValue(String.class));

                        String Img_uri=snapshot1.child("userImageUri").getValue(String.class);
                         user_phone=snapshot1.child("user_phone").getValue(String.class);
                         if (snapshot1.child("loginStatus").getValue(String.class).equals("1")){
                             sittingBtn.setVisibility(View.VISIBLE);
                           //  sittingBtn.setEnabled(true);

                         }else
                       //  if (snapshot1.child("loginStatus").getValue(String.class).equals("2")){
                             {

                             sittingBtn.setVisibility(View.VISIBLE);
                           //  sittingBtn.setEnabled(false);
                         }
                        loginStatus=snapshot1.child("loginStatus").getValue(String.class);
                        Glide.with(getContext()).load(Img_uri).into(imageView);
                        user_id=snapshot1.getKey();
                        if (snapshot1.child("cityName").getValue(String.class).equals("")){
                            city_txt.setVisibility(View.GONE);
                        }else {
                            city_txt.setVisibility(View.VISIBLE);
                            city_txt.setText("Lives in\t"+snapshot1.child("cityName").getValue(String.class));

                        }

                    }
                }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void fetchMyPOSTS() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase.getInstance().getReference().child("AdsPost").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren()) {

                    if (snapshot1.child("user_id").getValue(String.class).equals(Appdata.UID)) {

                        progressDialog.dismiss();


                        DataSnapshot contentSnapshot = snapshot1.child("/imageList");
                        List<String> listImage = new ArrayList<>();
                        Iterable<DataSnapshot> matchSnapShot = contentSnapshot.getChildren();
                        for (DataSnapshot match : matchSnapShot) {
                            String c = match.getValue(String.class);
                            listImage.add(c);
                            Log.d("ddd", listImage.get(0));


                        }
                        Log.d("ddd", listImage.size() + "");



                        String address = null;

                        try {
                            double latitude = Double.parseDouble((String) Objects.requireNonNull(snapshot1.child("latitude").getValue(String.class)));
                            double longitude = Double.parseDouble(Objects.requireNonNull(snapshot1.child("longitud").getValue(String.class)));
                            address = getAddress(latitude, longitude);
                            list.add(new AddspostModel(
                                    snapshot1.child("user_id").getValue(String.class),
                                    snapshot1.getKey(),
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
                                    "",
                                    "",
                                    snapshot1.child("videoUri").getValue(String.class),
                                    listImage


                            ));


                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }



                    }
                }
                profile_adapter=new Profile_adapeter(getActivity(),list);
                recyclerView.setAdapter(profile_adapter);
                profile_adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();

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

}