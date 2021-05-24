package com.variable.home_kode.Users.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class Edit_AdsActivity extends AppCompatActivity {

    EditText edtName;
    Spinner spinner_rooms, spinner_bathrooms, spinner_balcony, spinner_floor;
    /////////
    EditText edtAddress;
    CheckBox checkBoxd_drawing, checkBoxd_dinning, checkBoxd_drawing_dinning, checkBoxd_kitchen;
    String str_drawing, str_dinning, str_kitchen, str_houseadds, str_checkBoxd_drawing_dinning;
    String houseName_intent, spinner_rooms_intent, spinner_bathrooms_intent, spinner_balcony_intent, spinner_floo_intent;
    //////////
    EditText edtr_Rent, edtr_sq_feet, edtr_contactNo;
    CheckBox negotiable,fixed;
    Spinner spinner_month,spinner_area;
    String str_negotiablefixed;

    ////////////
    CheckBox checkbox_commoercialSPace,checkbox_sublet, checkbox_stall, checkBoxd_family, checkBoxd_bachelor, checkBoxd_office, checkBoxd_goddown, checkBoxd_garage;
    String str_CommercialSpace,str_checkBoxd_family, str_checkBoxd_bachelor, str_checkBoxd_office, str_checkBoxd_goddown, str_checkBoxd_garage, str_checkBox_stall, str_checkBox_sublet;
    //////////////
    CheckBox fireAlarm_checkbox,cctv_checkbox, secuirity_checkbox,
            cartaker_checkbox, checkboxgase, checkboxwater, checkbox_wifi, checkbox_Garage1, checkbox_lift1;
    String str_fireAlarm_checkbox,str_checkBoxd_secuirity, str_checkBoxd_cctv, str_checkBoxd_careTaker, str_gase, str_water, str_wifi, str_lift, str_fagarge1;
    /////////////////////
    CheckBox checkbox_flatId1,
            checkbox_duplexId1,
            checkbox_roomsType1,
            checkbox_freeSpacesId1,
            checkbox_tinShedId1,
            checkbox_othersProTypId1,
            checkbox_tiledId1,
            checkbox_non_tiledId1,
            checkbox_othersYardId1;
    String  str_checkbox_ProType,
            str_yardType;

    ////////////////
    EditText et_homeDetail;
    ProgressDialog progressDialog;
    Spinner spinnerr_city;
    MaterialButton updateBtnn;
    Bundle bundle;
    AddspostModel addspostModel;
    private View parent_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__ads);
        parent_view = findViewById(android.R.id.content);
         progressDialog=new ProgressDialog(this);
        bundle = getIntent().getExtras();
        addspostModel = (AddspostModel) bundle.getSerializable("MYDATA");


        edtName = findViewById(R.id.editTextTextPersonName);
        spinner_rooms = findViewById(R.id.rooms);
        spinner_bathrooms = findViewById(R.id.bathrooms);
        spinner_balcony = findViewById(R.id.balcony);
        spinner_floor = findViewById(R.id.floor);
        /////////////
        edtAddress = findViewById(R.id.editaddress);
        checkBoxd_drawing = findViewById(R.id.drawing);
        checkBoxd_dinning = findViewById(R.id.dinning);
        checkBoxd_drawing_dinning = findViewById(R.id.drawingdinning);
        checkBoxd_kitchen = findViewById(R.id.kitchen);
        //////////
        edtr_Rent = findViewById(R.id.editRent);
        edtr_sq_feet = findViewById(R.id.sq_feet);
        edtr_contactNo = findViewById(R.id.editphone);
        spinner_month = findViewById(R.id.month);
        negotiable = findViewById(R.id.negogtiableId);
        fixed = findViewById(R.id.fixedId);
        ////////////
        checkbox_commoercialSPace = findViewById(R.id.checkbox_commoercialSPace);
        checkBoxd_family = findViewById(R.id.checkbox_familyId);
        checkBoxd_bachelor = findViewById(R.id.checkbox_bachelor);
        checkBoxd_office = findViewById(R.id.checkbox_office);
        checkBoxd_goddown = findViewById(R.id.checkbox_goddown);
        checkBoxd_garage = findViewById(R.id.checkbox_garage);
        checkbox_stall = findViewById(R.id.checkbox_stall);
        checkbox_sublet = findViewById(R.id.checkbox_sublet);
///////////////////
        fireAlarm_checkbox = findViewById(R.id.fireAlarm);
        cctv_checkbox = findViewById(R.id.cctvId);
        secuirity_checkbox = findViewById(R.id.secuirityId);
        cartaker_checkbox = findViewById(R.id.caretaker);
        checkboxgase = findViewById(R.id.gasid);
        checkboxwater = findViewById(R.id.waterSupply);
        checkbox_wifi = findViewById(R.id.wifi);
        checkbox_lift1 = findViewById(R.id.checkbox_lift);
        checkbox_Garage1 = findViewById(R.id.checkbox_Garage1);
        ///////////////
        et_homeDetail = findViewById(R.id.editdetail);
        spinnerr_city = findViewById(R.id.spinner_city);
        spinner_area = findViewById(R.id.spinner_area);
//////////////////////

        checkbox_flatId1=findViewById(R.id.flatId);
        checkbox_duplexId1=findViewById(R.id.duplexId);
        checkbox_roomsType1=findViewById(R.id.roomsType);
        checkbox_freeSpacesId1=findViewById(R.id.freeSpacesId);
        checkbox_tinShedId1=findViewById(R.id.tinShedId);
        checkbox_othersProTypId1=findViewById(R.id.othersProTypId);
        checkbox_tiledId1=findViewById(R.id.tiledId);
        checkbox_non_tiledId1=findViewById(R.id.non_tiledId);
        checkbox_othersYardId1=findViewById(R.id.othersYardId);
/////////////////////////

        updateBtnn = findViewById(R.id.updateBtn);
        ///////////



        listeners(addspostModel);
        settngData(addspostModel);
        //////////

        str_checkbox_ProType="Flat";
        str_checkbox_ProType="Duplex";
        str_checkbox_ProType="Rooms";
        str_checkbox_ProType="Free Spaces";
        str_checkbox_ProType="Others";
        str_checkbox_ProType="Tin-Shed";
        str_yardType="Tiled";
        str_yardType="Non-Tiled";
        str_yardType="Others";

///
        str_negotiablefixed="Negotiable";
        str_negotiablefixed="Fixed";
        str_CommercialSpace="Commercial Space";
        str_fireAlarm_checkbox="Fire Alarm";

        ///

        str_checkBoxd_drawing_dinning = "Attached Dining and Drawing";
        str_dinning = "Dining";
        str_kitchen = "Kitchen";
        str_drawing = "Drawing";
        str_dinning = "Dining";
        str_checkBoxd_cctv = "CCTV";
        str_checkBoxd_family = "Family";
        str_checkBoxd_bachelor = "Bachelor";
        str_checkBoxd_office = "Office";
        str_checkBoxd_goddown = "Godown";
        str_checkBoxd_garage = "Garage";
        str_checkBox_stall = "Stall/Shop";
        str_checkBox_sublet = "Sub-let";
        str_checkBoxd_secuirity = "Security Guard";
        str_checkBoxd_careTaker = "Caretaker";
        str_gase = "Gas";
        str_wifi = "Wifi";
        str_water = "Water Supply";
        str_lift = "Elevator/Lift";
        str_fagarge1 = "Garage";


        if (str_checkbox_ProType.equals(addspostModel.proType)) {
            checkbox_othersProTypId1.setChecked(true);
        }
//        else
//        {
//            checkbox_othersProTypId1.setChecked(false);
//
//        }
            if (str_yardType.equals(addspostModel.yardType)) {
            checkbox_othersYardId1.setChecked(true);
        }
//            else
//            {
//                checkbox_othersYardId1.setChecked(false);
//
//            }

        if (str_CommercialSpace.equals(addspostModel.commercial)) {
            checkbox_commoercialSPace.setChecked(true);
        } else
        {
            checkbox_commoercialSPace.setChecked(false);

        }
            if (str_fireAlarm_checkbox.equals(addspostModel.alarm)) {
            fireAlarm_checkbox.setChecked(true);
        } else
            {
                fireAlarm_checkbox.setChecked(false);

            }
            if (str_negotiablefixed.equals(addspostModel.str_negotiablefixed)) {
            fixed.setChecked(true);
        }
//            else
//            {
//                fixed.setChecked(false);
//
//            }
            if (str_negotiablefixed.equals(addspostModel.str_negotiablefixed)) {
            negotiable.setChecked(true);
        }
//            else
//            {
//                negotiable.setChecked(false);
//
//            }



        if (str_checkBoxd_drawing_dinning.equals(addspostModel.str_draw_dinn)) {
            checkBoxd_drawing_dinning.setChecked(true);
        } else {
            checkBoxd_drawing_dinning.setChecked(false);
        }
        if (str_drawing.equals(addspostModel.str_drawing)) {
            checkBoxd_drawing.setChecked(true);
        } else {
            checkBoxd_drawing.setChecked(false);
        }

        if (str_dinning.equals(addspostModel.str_dinning)) {
            checkBoxd_dinning.setChecked(true);
        } else {
            checkBoxd_dinning.setChecked(false);
        }
        ///////
        if (str_kitchen.equals(addspostModel.str_kitchen)) {
            checkBoxd_kitchen.setChecked(true);
        } else {
            checkBoxd_kitchen.setChecked(false);
        }
///////////////////////2nd page rent for/////////////////////////////////
        if (str_checkBoxd_family.equals(addspostModel.checkBoxd_family)) {
            checkBoxd_family.setChecked(true);
        } else {
            checkBoxd_family.setChecked(false);
        }
        //////////////////////////
        if (str_checkBoxd_bachelor.equals(addspostModel.checkBoxd_bachelor)) {
            checkBoxd_bachelor.setChecked(true);
        } else {
            checkBoxd_bachelor.setChecked(false);
        }
        ///////////////////////////
        if (str_checkBoxd_office.equals(addspostModel.checkBoxd_office)) {
            checkBoxd_office.setChecked(true);
        } else {
            checkBoxd_office.setChecked(false);
        }
        //////////////////////////////
        if (str_checkBoxd_goddown.equals(addspostModel.checkBoxd_goddown)) {
            checkBoxd_goddown.setChecked(true);
        } else {
            checkBoxd_goddown.setChecked(false);
        }
        ///////////////////////////////////
        if (str_fagarge1.equals(addspostModel.checkBoxd_garage)) {
            checkBoxd_garage.setChecked(true);
        } else {
            checkBoxd_garage.setChecked(false);
        }
        ////////////////////////////////////
        if (str_checkBox_stall.equals(addspostModel.str_checkBox_stall11)) {
            checkbox_stall.setChecked(true);
        } else {
            checkbox_stall.setChecked(false);
        }

        ////////////////////////////Facilities////////////////////////////////////////////////////////
        if (str_checkBoxd_cctv.equals(addspostModel.cctv)) {
            cctv_checkbox.setChecked(true);
        } else {
            cctv_checkbox.setChecked(false);
        }
        //////////////////////////
        if (str_water.equals(addspostModel.water)) {
            checkboxwater.setChecked(true);
        } else {
            checkboxwater.setChecked(false);
        }
        ///////////////////////////
        if (str_checkBoxd_secuirity.equals(addspostModel.securityGuard))
        {
            secuirity_checkbox.setChecked(true);
        } else {
            secuirity_checkbox.setChecked(false);
        }
        //////////////////////////////
        if (str_wifi.equals(addspostModel.wifi)) {
            checkbox_wifi.setChecked(true);
        } else {
            checkbox_wifi.setChecked(false);
        }
        ///////////////////////////////////
        if (str_checkBoxd_careTaker.equals(addspostModel.caretaker)) {
            cartaker_checkbox.setChecked(true);
        } else {
            cartaker_checkbox.setChecked(false);
        }
        ////////////////////////////////////
        if (str_gase.equals(addspostModel.gas)) {
            checkboxgase.setChecked(true);
        } else {
            checkboxgase.setChecked(false);
        }
        if (str_lift.equals(addspostModel.str_lift)) {
            checkbox_lift1.setChecked(true);
        } else {
            checkbox_lift1.setChecked(false);
        }
        //////////////////////////////////

    }

    private void settngData(AddspostModel addspostModel) {
        edtName.setText(addspostModel.houseName);
        edtAddress.setText(addspostModel.address);
        edtr_Rent.setText(addspostModel.edtr_Rent);
      //  edtr_width.setText(addspostModel.str_fixed);
        edtr_sq_feet.setText(addspostModel.str_Sq_Feet);
        edtr_contactNo.setText(addspostModel.edtr_contactNo);
        et_homeDetail.setText(addspostModel.homeDetail);
         spinner_balcony.setSelection(Integer.parseInt(addspostModel.spinner_balcony));
         spinner_rooms.setSelection(Integer.parseInt(addspostModel.spinner_rooms));
         spinner_bathrooms.setSelection(Integer.parseInt(addspostModel.spinner_bathrooms));
        // spinner_floor.setSelection(Integer.parseInt(addspostModel.spinner_floor));

        // spinner_month.setSelection(Integer.parseInt(addspostModel.spinner_month));
       // spinnerr_city.setSelection(Integer.parseInt(addspostModel.spinner_city));
      //  ArrayAdapter<String> adapter = new ArrayAdapter<String>(Edit_AdsActivity.this, android.R.layout.simple_list_item_1, Collections.singletonList(addspostModel.spinner_floor));
       // adapter.setDropDownViewResource(R.layout.spinner_item);
     //   spinner_floor.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(Edit_AdsActivity.this, android.R.layout.simple_list_item_1, Collections.singletonList(addspostModel.spinner_city));
        // adapter.setDropDownViewResource(R.layout.spinner_item);
     //   spinnerr_city.setAdapter(adapter2);
      //  ArrayAdapter<String> adapter3 = new ArrayAdapter<String>(Edit_AdsActivity.this, android.R.layout.simple_list_item_1, Collections.singletonList(addspostModel.spinner_month));
        // adapter.setDropDownViewResource(R.layout.spinner_item);
     //   spinner_month.setAdapter(adapter3);

        ////////


    }

    private void listeners(AddspostModel addspostModel) {

        checkBoxd_drawing_dinning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    checkBoxd_drawing.setChecked(false);
                    checkBoxd_dinning.setChecked(false);
                    str_checkBoxd_drawing_dinning = "Attached Dining and Drawing";
                  //  Toast.makeText(Edit_AdsActivity.this, str_checkBoxd_drawing_dinning, Toast.LENGTH_SHORT).show();

                } else {
                    str_checkBoxd_drawing_dinning = "";

                }


            }
        });
//        //
        checkBoxd_dinning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    checkBoxd_drawing_dinning.setChecked(false);
                    str_dinning = "Dining";
                    //Toast.makeText(AddpostActivity2.this, str_dinning, Toast.LENGTH_SHORT).show();

                } else {
                    str_dinning = "";
                }


            }
        });
        checkBoxd_drawing.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                if (isChecked) {
                    checkBoxd_drawing_dinning.setChecked(false);
                    str_drawing = "Drawing";
                    //  Toast.makeText(AddpostActivity2.this, str_drawing, Toast.LENGTH_SHORT).show();

                } else {
                    str_drawing = "";
                }
            }
        });
//        ///
        checkBoxd_kitchen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    str_kitchen = "Kitchen";
//                    Toast.makeText(AddpostActivity2.this, str_kitchen, Toast.LENGTH_SHORT).show();

                } else {
                    str_kitchen = "";
                }
                //  checkBoxd_drawing_dinning.setChecked(false);
            }
        });
        /////////////////////////////////////////////////////
        checkBoxd_family.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){
                    str_checkBoxd_family="Family";
                }else {
                    str_checkBoxd_family="";

                }

            }
        });
        //
        checkBoxd_bachelor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkBoxd_bachelor="Bachelor";
                }else {
                    str_checkBoxd_bachelor="";

                }

            }
        });
        //
        checkBoxd_office.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    str_checkBoxd_office = "Office";
                } else{
                    str_checkBoxd_office = "";
                }

            }
        });
        //
        checkBoxd_goddown.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    str_checkBoxd_goddown="Godown";
                }else {
                    str_checkBoxd_goddown="";
                }

            }
        });
        //
        checkBoxd_garage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkBoxd_garage="Garage";
                }else {
                    str_checkBoxd_garage="";
                }

            }
        });

        //
        checkbox_stall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkBox_stall="Stall/Shop";
                }else {
                    str_checkBox_stall="";

                }

            }
        });
        //
        checkbox_sublet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkBox_sublet="Sub-let";
                }else {
                    str_checkBox_sublet="";

                }

            }
        });
        ////////////////////////////////

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
            checkbox_Garage1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked){
                        str_fagarge1="Garage";
                    }else {
                        str_fagarge1="";
                    }
                }
            });

            ///////////////
        checkbox_flatId1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkbox_ProType="Flat";
                    checkbox_duplexId1.setChecked(false);
                    checkbox_roomsType1.setChecked(false);
                    checkbox_freeSpacesId1.setChecked(false);
                    checkbox_othersProTypId1.setChecked(false);
                    checkbox_tinShedId1.setChecked(false);

                }else {
                    str_checkbox_ProType="";

                }
            }
        });
        checkbox_duplexId1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkbox_ProType="Duplex";

                    checkbox_flatId1.setChecked(false);
                    checkbox_roomsType1.setChecked(false);
                    checkbox_freeSpacesId1.setChecked(false);
                    checkbox_othersProTypId1.setChecked(false);
                    checkbox_tinShedId1.setChecked(false);

                }else {
                    str_checkbox_ProType="";
                }
            }
        });
        checkbox_roomsType1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkbox_ProType="Rooms";
                    checkbox_flatId1.setChecked(false);
                    checkbox_duplexId1.setChecked(false);
                    checkbox_freeSpacesId1.setChecked(false);
                    checkbox_othersProTypId1.setChecked(false);
                    checkbox_tinShedId1.setChecked(false);

                }else {
                    str_checkbox_ProType="";
                }
            }
        }); checkbox_freeSpacesId1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkbox_ProType="Free Spaces";
                    checkbox_flatId1.setChecked(false);
                    checkbox_duplexId1.setChecked(false);
                    checkbox_roomsType1.setChecked(false);
                    checkbox_othersProTypId1.setChecked(false);
                    checkbox_tinShedId1.setChecked(false);

                }else {
                    str_checkbox_ProType="";
                }
            }
        });
        checkbox_othersProTypId1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkbox_ProType="Others";
                    checkbox_flatId1.setChecked(false);
                    checkbox_duplexId1.setChecked(false);
                    checkbox_roomsType1.setChecked(false);
                    checkbox_freeSpacesId1.setChecked(false);
                    checkbox_tinShedId1.setChecked(false);

                }else {
                    str_checkbox_ProType="";
                }
            }
        });
        checkbox_tinShedId1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_checkbox_ProType="Tin-Shed";
                    //  Toast.makeText(AddpostActivity7.this, "Tin-Shed", Toast.LENGTH_SHORT).show();
                    checkbox_flatId1.setChecked(false);
                    checkbox_duplexId1.setChecked(false);
                    checkbox_roomsType1.setChecked(false);
                    checkbox_freeSpacesId1.setChecked(false);
                    checkbox_othersProTypId1.setChecked(false);


                }else {
                    str_checkbox_ProType="";
                }
            }
        });
        //////////////////////////////////////////////
        checkbox_tiledId1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_yardType="Tiled";
                    checkbox_non_tiledId1.setChecked(false);
                    checkbox_othersYardId1.setChecked(false);


                }else {
                    str_yardType="";
                }
            }
        });
        checkbox_non_tiledId1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_yardType="Non-Tiled";
                    checkbox_tiledId1.setChecked(false);
                    checkbox_othersYardId1.setChecked(false);

                }else {
                    str_yardType="";
                }
            }
        });
        checkbox_othersYardId1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_yardType="Others";
                    checkbox_tiledId1.setChecked(false);
                    checkbox_non_tiledId1.setChecked(false);

                }else {
                    str_yardType="";
                }
            }
        });


        updateBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                String str_Housename = edtName.getText().toString().trim();
                String str_edtAddress = edtAddress.getText().toString().trim();
                String str_edtr_Rent=edtr_Rent.getText().toString().trim();
                String str_sq_feet=edtr_sq_feet.getText().toString().trim();
                String str_edtr_contactNo=edtr_contactNo.getText().toString().trim();
                String str_et_homeDetail=et_homeDetail.getText().toString().trim();

                if (TextUtils.isEmpty(str_Housename)) {
                    edtName.setError("Enter House Name");
                    Snackbar.make(parent_view, "Enter House Name", Snackbar.LENGTH_SHORT).show();
                    return;
                    // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else if (spinner_rooms.getSelectedItem().toString().equals("No of Living Rooms")) {
                    Snackbar.make(parent_view, "please Choose No of Living Rooms", Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (spinner_bathrooms.getSelectedItem().toString().equals("No of Bathroom")) {
                    Snackbar.make(parent_view, "please Choose No of Bathroom", Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (spinner_balcony.getSelectedItem().toString().equals("No of Balcony")) {
                    Snackbar.make(parent_view, "please Choose No of Balcony", Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (spinner_floor.getSelectedItem().toString().equals("Select the floor")) {
                    Snackbar.make(parent_view, "please Choose No of Floor", Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(str_edtAddress)) {
                    edtAddress.setError("Enter House Address");
                    Snackbar.make(parent_view, "Enter House Address", Snackbar.LENGTH_SHORT).show();
                    return;
                    // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else
                if (TextUtils.isEmpty(str_edtr_Rent)) {
                    edtr_Rent.setError("Enter House Rent");
                    Snackbar.make(parent_view, "Enter House Rent", Snackbar.LENGTH_SHORT).show();
                    return;
                    // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
//
        if (TextUtils.isEmpty(str_sq_feet)) {
            edtr_sq_feet.setError("Enter Sq/Feet");
            Snackbar.make(parent_view, "Enter Sq/Feet", Snackbar.LENGTH_SHORT).show();
            return;
            // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        else
            if (spinner_month.getSelectedItem().toString().equals("Available Month")) {
            Snackbar.make(parent_view, "please Choose Available Month", Snackbar.LENGTH_SHORT).show();
            return;
        }
                else if (TextUtils.isEmpty(str_edtr_contactNo)) {
                    edtr_contactNo.setError("Enter contact No");
                    Snackbar.make(parent_view, "Enter contact No", Snackbar.LENGTH_SHORT).show();
                    return;
                    // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (spinner_month.getSelectedItem().toString().equals("Available Month")){
                    Toast.makeText(Edit_AdsActivity.this, "Select Available Month.!", Toast.LENGTH_SHORT).show();
                    return;
                }

                else
                if (spinnerr_city.getSelectedItem().toString().equals("Select District")){
                    Toast.makeText(Edit_AdsActivity.this, "Select District Please..!", Toast.LENGTH_SHORT).show();
                    return;
                }else
                    if (spinner_area.getSelectedItem().toString().equals("Select Area")){
                    Toast.makeText(Edit_AdsActivity.this, "Select Area Please..!", Toast.LENGTH_SHORT).show();
                    return;
                }else {


                    Geocoder coder = new Geocoder(Edit_AdsActivity.this);
                    List<Address> address = null;

                    try {
                        address = coder.getFromLocationName(str_edtAddress, 5);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Address location = address.get(0);
                    location.getLatitude();
                    location.getLongitude();

                    double latitude = location.getLatitude();
                    double longitud = location.getLongitude();
                    String str_lati = String.valueOf(latitude);
                    String str_longi = String.valueOf(longitud);




                    progressDialog.show();
                    progressDialog.setTitle("Edit ads");
                    progressDialog.setMessage("Please wait until the data is loaded...!");
                    progressDialog.setCancelable(false);
                    FirebaseAuth mAuth = FirebaseAuth.getInstance();
                    //reference=FirebaseDatabase.getInstance().getReference().child("AdsPost").push();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("AdsPost").child(addspostModel.post_id);

                    ref.child("user_id").setValue(Appdata.UID);

                        ref.child("yardType").setValue(str_yardType);
                        ref.child("proType").setValue(str_checkbox_ProType);
                        ref.child("commercial_space").setValue(str_CommercialSpace);
                        ref.child("str_fireAlarm").setValue(str_fireAlarm_checkbox);
                        ref.child("area").setValue(spinner_area.getSelectedItem().toString());
                        ref.child("str_negotiablefixed").setValue(str_negotiablefixed);



                    ref.child("spinner_floor").setValue(spinner_floor.getSelectedItem().toString());
                    ref.child("spinner_balcony").setValue(spinner_balcony.getSelectedItem().toString());
                    ref.child("spinner_bathrooms").setValue(spinner_bathrooms.getSelectedItem().toString());
                    ref.child("spinner_rooms").setValue(spinner_rooms.getSelectedItem().toString());
                    ref.child("houseName").setValue(str_Housename);
                    ref.child("str_kitchen").setValue(str_kitchen);
                    ref.child("str_drawing").setValue(str_drawing);
                    ref.child("str_dinning").setValue(str_dinning);
                    ref.child("checkBoxd_drawing").setValue(str_checkBoxd_drawing_dinning);
                    ref.child("latitude").setValue(str_lati);
                    ref.child("longitud").setValue(str_longi);
                    ref.child("spinner_month").setValue(spinner_month.getSelectedItem().toString());
                    ref.child("edtr_contactNo").setValue(str_edtr_contactNo);
                    ref.child("str_Sq_Feet").setValue(str_sq_feet);
                    ref.child("edtr_Rent").setValue(str_edtr_Rent);
                    ref.child("checkBoxd_garage").setValue(str_checkBoxd_garage);
                    ref.child("checkBoxd_goddown").setValue(str_checkBoxd_goddown);
                    ref.child("checkBoxd_office").setValue(str_checkBoxd_office);
                    ref.child("checkBoxd_bachelor").setValue(str_checkBoxd_bachelor);
                    ref.child("checkBoxd_family").setValue(str_checkBoxd_family);
                    ref.child("gas").setValue(str_gase);
                    ref.child("water").setValue(str_water);
                    ref.child("wifi").setValue(str_wifi);
                    ref.child("caretaker").setValue(str_checkBoxd_careTaker);
                    ref.child("securityGuard").setValue(str_checkBoxd_secuirity);
                    ref.child("cctv").setValue(str_checkBoxd_cctv);
                    ref.child("homeDetail").setValue(str_et_homeDetail);
                    ref.child("str_checkBox_stall11").setValue(str_checkBox_stall);
                    ref.child("str_checkBox_sublet11").setValue(str_checkBox_sublet);
                    ref.child("str_fagarge11").setValue(str_fagarge1);
                    ref.child("str_lift1").setValue(str_lift);
                    ref.child("spinner_city").setValue(spinnerr_city.getSelectedItem().toString()).
                   // ref.child("imageList").setValue(url).
                            addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(Edit_AdsActivity.this, "Ad updated Successfully..!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Edit_AdsActivity.this,User_DashboardpActivity.class));
                                finish();

                            } else {
                                progressDialog.dismiss();
                                Toast.makeText(Edit_AdsActivity.this, "Failed", Toast.LENGTH_SHORT).show();                  }
                        }
                    });


                }
                }

        });
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Edit_AdsActivity.this,User_DashboardpActivity.class));
        finish();    }

    public void backEditAds(View view) {
        startActivity(new Intent(Edit_AdsActivity.this,User_DashboardpActivity.class));
        finish();
    }
}
