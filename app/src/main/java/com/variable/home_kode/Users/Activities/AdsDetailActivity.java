package com.variable.home_kode.Users.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Adapters.PageAdapter;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.util.ArrayList;
import java.util.List;

public class AdsDetailActivity extends AppCompatActivity {
//   ImageView imageSlider;


    TabLayout tabLayout;
    TabItem tabItem1,tabItem2;
    //tabItem3;
    ViewPager viewPager;
  public static AddspostModel addspostModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ads_detail);


        tabLayout=findViewById(R.id.tabID);
        tabItem1=findViewById(R.id.tab1);
        tabItem2=findViewById(R.id.tab2);
      //  tabItem3=findViewById(R.id.tab3);

        //   def=item2.getTextColors();
        viewPager=findViewById(R.id.viewPagerId);

        final PageAdapter pageAdapter=new PageAdapter(getSupportFragmentManager(),tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition()==0 || tab.getPosition()==1 ){
                        //|| tab.getPosition()==2){
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));














//
//        List<SlideModel> imgList=new ArrayList<>();
        Bundle bundle=getIntent().getExtras();
         addspostModel= (AddspostModel) bundle.getSerializable("MYDATA");

//      //  imgList.add(new SlideModel(String.valueOf(addspostModel.imageList), ScaleTypes.FIT));
//

    }

    public void backDtail(View view) {
        startActivity(new Intent(AdsDetailActivity.this,User_DashboardpActivity.class));
        finish();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AdsDetailActivity.this,User_DashboardpActivity.class));
        finish();
    }
}