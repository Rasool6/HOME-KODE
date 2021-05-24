package com.variable.home_kode.Users.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.arch.core.util.Function;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.FRAGMENTS.Home_frg;
import com.variable.home_kode.Users.FRAGMENTS.LocationBaseAds_frg;
import com.variable.home_kode.Users.FRAGMENTS.Profile_frg;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;


public class User_DashboardpActivity extends AppCompatActivity {
  public MeowBottomNavigation cbn;
    public   static final int ID_HOME=1;
    public   static final int ID_LOCATION=2;
    public   static final int ID_PROFILE=3;
    FloatingActionButton addPostbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user__dashboardp);

         cbn = findViewById(R.id.menu);
         addPostbtn = findViewById(R.id.floatingbtn);




        getSupportFragmentManager().beginTransaction().replace(R.id.container,new Home_frg()).commit();


        cbn.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home1));
        cbn.add(new MeowBottomNavigation.Model(2, R.drawable.ic_location));
        cbn.add(new MeowBottomNavigation.Model(3, R.drawable.ic_account));


///
        cbn.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
             //   Toast.makeText(User_DashboardpActivity.this, "clicked"+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });

        cbn.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment selectedFrg=null;
                switch (item.getId()){
                    case ID_HOME:
                        selectedFrg=new Home_frg();
                        break;
                    case ID_LOCATION:
                        selectedFrg=new LocationBaseAds_frg();
                        break;
                    case ID_PROFILE:
                        selectedFrg=new Profile_frg();
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.container,selectedFrg).commit();

            }
        });

        cbn.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                // your codes
            }
        });


        addPostbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(User_DashboardpActivity.this,AddpostActivity1.class));

            }
        });

            }

    @Override
    public void onBackPressed() {
        IsFinish("Want to close app?");
    }

   public  void IsFinish(String alertmessage) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        android.os.Process.killProcess(android.os.Process.myPid());
                        // This above line close correctly
                        finish();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(alertmessage)
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

}

