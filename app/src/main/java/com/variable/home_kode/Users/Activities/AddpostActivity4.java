package com.variable.home_kode.Users.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;

import com.variable.home_kode.R;

public class AddpostActivity4 extends AppCompatActivity {
    EditText edtAddress;
    CheckBox checkbox_sublet,commercial_Space,checkbox_stall,checkBoxd_family,checkBoxd_bachelor,checkBoxd_office,checkBoxd_goddown,checkBoxd_garage;
    private View parent_view;
    String str_checkBoxd_family="";
    String str_checkBoxd_bachelor="";
    String str_checkBoxd_office="";
    String str_checkBoxd_goddown="";
    String str_checkBoxd_garage="";
    String str_checkBox_stall="";
    String str_checkBox_sublet="";
    String str_checkBox_Commercial_Space="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost4);

        parent_view = findViewById(android.R.id.content);

        checkBoxd_family=findViewById(R.id.checkbox_familyId);
        checkBoxd_bachelor=findViewById(R.id.checkbox_bachelor);
        checkBoxd_office=findViewById(R.id.checkbox_office);
        checkBoxd_goddown=findViewById(R.id.checkbox_goddown);
        checkBoxd_garage=findViewById(R.id.checkbox_garage);
        checkbox_stall=findViewById(R.id.checkbox_stall);
        checkbox_sublet=findViewById(R.id.checkbox_sublet);
        commercial_Space=findViewById(R.id.checkbox_commoercialSPace);

        checkedbox();
    }

    private void checkedbox() {
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
        commercial_Space.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
               if (isChecked){
                   str_checkBox_Commercial_Space="Commercial Space";
               }else {
                   str_checkBox_Commercial_Space="";

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
    }

    public void next4(View view) {


                startActivity(new Intent(AddpostActivity4.this,AddpostActivity5.class)
                .putExtra("str_checkBox_sublet4",str_checkBox_sublet)
                .putExtra("str_checkBox_stall4",str_checkBox_stall)
                .putExtra("str_checkBoxd_family4",str_checkBoxd_family)
                .putExtra("str_checkBoxd_bachelor4",str_checkBoxd_bachelor)
                .putExtra("str_checkBoxd_office4",str_checkBoxd_office)
                .putExtra("str_checkBoxd_goddown4",str_checkBoxd_goddown)
                .putExtra("str_checkBoxd_garage4",str_checkBoxd_garage)
                .putExtra("str_checkBox_Commercial_Space4",str_checkBox_Commercial_Space)

                .putExtra("str_edtr_Rent4",getIntent().getStringExtra("str_edtr_Rent3"))
                .putExtra("str_negotiablefixed4",getIntent().getStringExtra("str_negotiablefixed3"))
                .putExtra("str_edtr_Sq_Feet4",getIntent().getStringExtra("str_edtr_Sq_Feet3"))
                .putExtra("str_edtr_contactNo4",getIntent().getStringExtra("str_edtr_contactNo3"))
                .putExtra("spinner_month4",getIntent().getStringExtra("spinner_month3"))
                .putExtra("latitude4", getIntent().getStringExtra("latitude3"))
                .putExtra("longitud4", getIntent().getStringExtra("longitud3"))
                .putExtra("str_checkBoxd_drawing_dinning4", getIntent().getStringExtra("str_checkBoxd_drawing_dinning3"))
                .putExtra("str_dinning4", getIntent().getStringExtra("str_dinning3"))
                .putExtra("str_drawing4", getIntent().getStringExtra("str_drawing3"))
                .putExtra("str_kitchen4", getIntent().getStringExtra("str_kitchen3"))
                .putExtra("houseName4", getIntent().getStringExtra("houseName3"))
                .putExtra("spinner_rooms4", getIntent().getStringExtra("spinner_rooms3"))
                .putExtra("spinner_bathrooms4", getIntent().getStringExtra("spinner_bathrooms3"))
                .putExtra("spinner_balcony4", getIntent().getStringExtra("spinner_balcony3"))
                .putExtra("spinner_floor4", getIntent().getStringExtra("spinner_floor3"))
        );

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddpostActivity4.this,AddpostActivity3.class));
        finish();
    }

    public void back4(View view) {
        startActivity(new Intent(AddpostActivity4.this,AddpostActivity3.class));
        finish();
    }
}