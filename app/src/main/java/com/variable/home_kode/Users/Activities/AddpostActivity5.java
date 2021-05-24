package com.variable.home_kode.Users.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.variable.home_kode.R;

public class AddpostActivity5 extends AppCompatActivity {
    CheckBox cctv_checkbox,secuirity_checkbox,fireAlarm_checkbox,
            cartaker_checkbox,checkboxgase, checkboxwater, checkbox_wifi,checkbox_GarageF,
            checkbox_lift1,
            checkbox_flatId1,
            checkbox_duplexId1,
            checkbox_roomsType1,
            checkbox_freeSpacesId1,
            checkbox_tinShedId1,
            checkbox_othersProTypId1,
            checkbox_tiledId1,
            checkbox_non_tiledId1,
            checkbox_othersYardId1;
    String str_checkBoxd_secuirity="";
    String str_fireAlarm_checkbox="";
    String str_checkBoxd_cctv="";
    String str_checkBoxd_careTaker="";
    String str_gase="";
    String str_water="";
    String str_wifi="";
    String str_lift="";
    String str_fagarge1="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost5);
        cctv_checkbox=findViewById(R.id.cctvId);
        secuirity_checkbox=findViewById(R.id.secuirityId);
        cartaker_checkbox=findViewById(R.id.caretaker);
        checkboxgase=findViewById(R.id.gasid);
        checkboxwater =findViewById(R.id.waterSupply);
        checkbox_wifi=findViewById(R.id.wifi);
        checkbox_lift1=findViewById(R.id.checkbox_lift);
        checkbox_GarageF=findViewById(R.id.checkbox_Garage1);
        fireAlarm_checkbox=findViewById(R.id.fireAlarm);

        cctv_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    str_checkBoxd_cctv = "CCTV";


                } else {
                    str_checkBoxd_cctv = "";
                }
            }
        });
        //
        secuirity_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    str_checkBoxd_secuirity="Security Guard";

                }else {
                    str_checkBoxd_secuirity="";

                }
            }
        });
        //
        cartaker_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    str_checkBoxd_careTaker="Caretaker";
                }else {
                    str_checkBoxd_careTaker="";
                }
            }
        });
        //
        checkboxgase.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    str_gase="Gas";

                }else {
                    str_gase="";

                }
            }
        });
        //
        checkbox_wifi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_wifi="Wifi";

                }else {
                    str_wifi="";

                }
            }
        });
        //
        checkboxwater.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    str_water="Water Supply";

                }else {
                    str_water="";
                }
            }
        });
        checkbox_lift1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_lift="Elevator/Lift";
                }else {
                    str_lift="";
                }
            }
        });
        checkbox_GarageF.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_fagarge1="Garage";
                }else {
                    str_fagarge1="";
                }
            }
        });

        fireAlarm_checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_fireAlarm_checkbox="Fire Alarm";
                }else {
                    str_fireAlarm_checkbox="";
                }
            }
        });
        ////////////////////////////////////////////

    }

    public void next5(View view) {


        startActivity(new Intent(AddpostActivity5.this, AddpostActivity6.class)


                .putExtra("str_fireAlarm_checkbox5",str_fireAlarm_checkbox)
                .putExtra("cctv5",str_checkBoxd_cctv)
                .putExtra("securityGuard5",str_checkBoxd_secuirity)
                .putExtra("caretaker5",str_checkBoxd_careTaker)
                .putExtra("wifi5",str_wifi)
                .putExtra("water5",str_water)
                .putExtra("gas5",str_gase)
                .putExtra("str_lift5",str_lift)
                .putExtra("str_fagarge5",str_fagarge1)

                 .putExtra("str_checkBox_sublet5",getIntent().getStringExtra("str_checkBox_sublet4"))
                .putExtra("str_checkBox_stall5",getIntent().getStringExtra("str_checkBox_stall4"))
                .putExtra("str_checkBoxd_family5",getIntent().getStringExtra("str_checkBoxd_family4"))
                .putExtra("str_checkBoxd_bachelor5",getIntent().getStringExtra("str_checkBoxd_bachelor4"))
                .putExtra("str_checkBoxd_office5",getIntent().getStringExtra("str_checkBoxd_office4"))
                .putExtra("str_checkBoxd_goddown5",getIntent().getStringExtra("str_checkBoxd_goddown4"))
                .putExtra("str_checkBoxd_garage5",getIntent().getStringExtra("str_checkBoxd_garage4"))
                .putExtra("str_checkBox_Commercial_Space5",getIntent().getStringExtra("str_checkBox_Commercial_Space4"))

                .putExtra("str_edtr_Rent5",getIntent().getStringExtra("str_edtr_Rent4"))
                .putExtra("str_negotiablefixed5",getIntent().getStringExtra("str_negotiablefixed4"))
                .putExtra("str_edtr_Sq_Feet5",getIntent().getStringExtra("str_edtr_Sq_Feet4"))
                .putExtra("str_edtr_contactNo5",getIntent().getStringExtra("str_edtr_contactNo4"))
                .putExtra("spinner_month5",getIntent().getStringExtra("spinner_month4"))
                .putExtra("latitude5", getIntent().getStringExtra("latitude4"))
                .putExtra("longitud5", getIntent().getStringExtra("longitud4"))
                .putExtra("str_checkBoxd_drawing_dinning5", getIntent().getStringExtra("str_checkBoxd_drawing_dinning4"))
                .putExtra("str_dinning5", getIntent().getStringExtra("str_dinning4"))
                .putExtra("str_drawing5", getIntent().getStringExtra("str_drawing4"))
                .putExtra("str_kitchen5", getIntent().getStringExtra("str_kitchen4"))
                .putExtra("houseName5", getIntent().getStringExtra("houseName4"))
                .putExtra("spinner_rooms5", getIntent().getStringExtra("spinner_rooms4"))
                .putExtra("spinner_bathrooms5", getIntent().getStringExtra("spinner_bathrooms4"))
                .putExtra("spinner_balcony5", getIntent().getStringExtra("spinner_balcony4"))
                .putExtra("spinner_floor5", getIntent().getStringExtra("spinner_floor4"))
        );
    }



    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddpostActivity5.this,AddpostActivity4.class));
        finish();
    }

    public void back5(View view) {
        startActivity(new Intent(AddpostActivity5.this,AddpostActivity4.class));
        finish();
    }
}