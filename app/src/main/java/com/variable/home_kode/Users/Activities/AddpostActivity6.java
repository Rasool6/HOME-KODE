package com.variable.home_kode.Users.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.toptoche.searchablespinnerlibrary.SearchableSpinner;
import com.variable.home_kode.R;

public class AddpostActivity6 extends AppCompatActivity {
    EditText et_homeDetail;
    ProgressDialog progressDialog;
    SearchableSpinner spinnerr_city,spinner_area;
    // TextView textView;
    // Button choose,send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost6);
        progressDialog = new ProgressDialog(this);
        et_homeDetail = findViewById(R.id.editdetail);
        spinnerr_city = findViewById(R.id.spinner_city);
        spinner_area = findViewById(R.id.spinner_area);
    }

    public void next8(View view) {
        String str_et_homeDetail = et_homeDetail.getText().toString().trim();
        progressDialog.show();
        progressDialog.setTitle("Post");
        progressDialog.setMessage("Upload data please wait ...!");
        progressDialog.setCancelable(false);
        if (spinnerr_city.getSelectedItem().toString().equals("Select City")) {
            Toast.makeText(this, "Select City Please..!", Toast.LENGTH_SHORT).show();
            return;
        } else
            if (spinner_area.getSelectedItem().toString().equals("Select Area")) {
                Toast.makeText(this, "Select Area Please..!", Toast.LENGTH_SHORT).show();
                return;
            } else {

                startActivity(new Intent(AddpostActivity6.this, AddpostActivity7.class)





                        .putExtra("spinner_area6", spinner_area.getSelectedItem().toString())
                        .putExtra("spinner_city6", spinnerr_city.getSelectedItem().toString())
                        .putExtra("et_homeDetail6", str_et_homeDetail)
                        .putExtra("str_fireAlarm_checkbox6",getIntent().getStringExtra("str_fireAlarm_checkbox5"))
                        .putExtra("cctv6",getIntent().getStringExtra("cctv5"))
                        .putExtra("securityGuard6",getIntent().getStringExtra("securityGuard5"))
                        .putExtra("caretaker6",getIntent().getStringExtra("caretaker5"))
                        .putExtra("wifi6",getIntent().getStringExtra("wifi5"))
                        .putExtra("water6",getIntent().getStringExtra("water5"))
                        .putExtra("gas6",getIntent().getStringExtra("gas5"))
                        .putExtra("str_lift6",getIntent().getStringExtra("str_lift5"))
                        .putExtra("str_fagarge6",getIntent().getStringExtra("str_fagarge5"))

                        .putExtra("str_checkBox_sublet6",getIntent().getStringExtra("str_checkBox_sublet5"))
                        .putExtra("str_checkBox_stall6",getIntent().getStringExtra("str_checkBox_stall5"))
                        .putExtra("str_checkBoxd_family6",getIntent().getStringExtra("str_checkBoxd_family5"))
                        .putExtra("str_checkBoxd_bachelor6",getIntent().getStringExtra("str_checkBoxd_bachelor5"))
                        .putExtra("str_checkBoxd_office6",getIntent().getStringExtra("str_checkBoxd_office5"))
                        .putExtra("str_checkBoxd_goddown6",getIntent().getStringExtra("str_checkBoxd_goddown5"))
                        .putExtra("str_checkBoxd_garage6",getIntent().getStringExtra("str_checkBoxd_garage5"))
                        .putExtra("str_checkBox_Commercial_Space6",getIntent().getStringExtra("str_checkBox_Commercial_Space5"))

                        .putExtra("str_edtr_Rent6",getIntent().getStringExtra("str_edtr_Rent5"))
                        .putExtra("str_negotiablefixed6",getIntent().getStringExtra("str_negotiablefixed5"))
                        .putExtra("str_edtr_Sq_Feet6",getIntent().getStringExtra("str_edtr_Sq_Feet5"))
                        .putExtra("str_edtr_contactNo6",getIntent().getStringExtra("str_edtr_contactNo5"))
                        .putExtra("spinner_month6",getIntent().getStringExtra("spinner_month5"))
                        .putExtra("latitude6", getIntent().getStringExtra("latitude5"))
                        .putExtra("longitud6", getIntent().getStringExtra("longitud5"))
                        .putExtra("str_checkBoxd_drawing_dinning6", getIntent().getStringExtra("str_checkBoxd_drawing_dinning5"))
                        .putExtra("str_dinning6", getIntent().getStringExtra("str_dinning5"))
                        .putExtra("str_drawing6", getIntent().getStringExtra("str_drawing5"))
                        .putExtra("str_kitchen6", getIntent().getStringExtra("str_kitchen5"))
                        .putExtra("houseName6", getIntent().getStringExtra("houseName5"))
                        .putExtra("spinner_rooms6", getIntent().getStringExtra("spinner_rooms5"))
                        .putExtra("spinner_bathrooms6", getIntent().getStringExtra("spinner_bathrooms5"))
                        .putExtra("spinner_balcony6", getIntent().getStringExtra("spinner_balcony5"))
                        .putExtra("spinner_floor6", getIntent().getStringExtra("spinner_floor5"))
                );
            }
//        startActivity(new Intent(AddpostActivity8.this,User_DashboardpActivity.class)
//        );

        }


    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddpostActivity6.this,AddpostActivity5.class));
        finish();
    }

    public void back6(View view) {
        startActivity(new Intent(AddpostActivity6.this,AddpostActivity5.class));
        finish();
    }


}