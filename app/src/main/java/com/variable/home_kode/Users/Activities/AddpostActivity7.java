package com.variable.home_kode.Users.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Model.VideoModel;

import java.lang.reflect.Member;

public class AddpostActivity7 extends AppCompatActivity {

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost7);

        checkbox_flatId1=findViewById(R.id.flatId);
        checkbox_duplexId1=findViewById(R.id.duplexId);
        checkbox_roomsType1=findViewById(R.id.roomsType);
        checkbox_freeSpacesId1=findViewById(R.id.freeSpacesId);
        checkbox_tinShedId1=findViewById(R.id.tinShedId);
        checkbox_othersProTypId1=findViewById(R.id.othersProTypId);
        checkbox_tiledId1=findViewById(R.id.tiledId);
        checkbox_non_tiledId1=findViewById(R.id.non_tiledId);
        checkbox_othersYardId1=findViewById(R.id.othersYardId);

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

                }
            }
        });



    }

    public void next7(View view) {


        startActivity(new Intent(AddpostActivity7.this, AddpostActivity8.class)
                .putExtra("str_yardType7",str_yardType)
                .putExtra("str_checkbox_ProType7",str_checkbox_ProType)


                .putExtra("spinner_area7",getIntent().getStringExtra("spinner_area6") )
                .putExtra("spinner_city7",getIntent().getStringExtra("spinner_city6"))
                .putExtra("et_homeDetail7", getIntent().getStringExtra("et_homeDetail6"))
                .putExtra("str_fireAlarm_checkbox7",getIntent().getStringExtra("str_fireAlarm_checkbox6"))
                .putExtra("cctv7",getIntent().getStringExtra("cctv6"))
                .putExtra("securityGuard7",getIntent().getStringExtra("securityGuard6"))
                .putExtra("caretaker7",getIntent().getStringExtra("caretaker6"))
                .putExtra("wifi7",getIntent().getStringExtra("wifi6"))
                .putExtra("water7",getIntent().getStringExtra("water6"))
                .putExtra("gas7",getIntent().getStringExtra("gas6"))
                .putExtra("str_lift7",getIntent().getStringExtra("str_lift6"))
                .putExtra("str_fagarge7",getIntent().getStringExtra("str_fagarge6"))

                .putExtra("str_checkBox_sublet7",getIntent().getStringExtra("str_checkBox_sublet6"))
                .putExtra("str_checkBox_stall7",getIntent().getStringExtra("str_checkBox_stall6"))
                .putExtra("str_checkBoxd_family7",getIntent().getStringExtra("str_checkBoxd_family6"))
                .putExtra("str_checkBoxd_bachelor7",getIntent().getStringExtra("str_checkBoxd_bachelor6"))
                .putExtra("str_checkBoxd_office7",getIntent().getStringExtra("str_checkBoxd_office6"))
                .putExtra("str_checkBoxd_goddown7",getIntent().getStringExtra("str_checkBoxd_goddown6"))
                .putExtra("str_checkBoxd_garage7",getIntent().getStringExtra("str_checkBoxd_garage6"))
                .putExtra("str_checkBox_Commercial_Space7",getIntent().getStringExtra("str_checkBox_Commercial_Space6"))

                .putExtra("str_edtr_Rent7",getIntent().getStringExtra("str_edtr_Rent6"))
                .putExtra("str_negotiablefixed7",getIntent().getStringExtra("str_negotiablefixed6"))
                .putExtra("str_edtr_Sq_Feet7",getIntent().getStringExtra("str_edtr_Sq_Feet6"))
                .putExtra("str_edtr_contactNo7",getIntent().getStringExtra("str_edtr_contactNo6"))
                .putExtra("spinner_month7",getIntent().getStringExtra("spinner_month6"))
                .putExtra("latitude7", getIntent().getStringExtra("latitude6"))
                .putExtra("longitud7", getIntent().getStringExtra("longitud6"))
                .putExtra("str_checkBoxd_drawing_dinning7", getIntent().getStringExtra("str_checkBoxd_drawing_dinning6"))
                .putExtra("str_dinning7", getIntent().getStringExtra("str_dinning6"))
                .putExtra("str_drawing7", getIntent().getStringExtra("str_drawing6"))
                .putExtra("str_kitchen7", getIntent().getStringExtra("str_kitchen6"))
                .putExtra("houseName7", getIntent().getStringExtra("houseName6"))
                .putExtra("spinner_rooms7", getIntent().getStringExtra("spinner_rooms6"))
                .putExtra("spinner_bathrooms7", getIntent().getStringExtra("spinner_bathrooms6"))
                .putExtra("spinner_balcony7", getIntent().getStringExtra("spinner_balcony6"))
                .putExtra("spinner_floor7", getIntent().getStringExtra("spinner_floor6"))
        );

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddpostActivity7.this,AddpostActivity6.class));
        finish();
    }

    public void back7(View view) {
        startActivity(new Intent(AddpostActivity7.this,AddpostActivity6.class));
        finish();
    }
}
//// Pause the upload
//uploadTask.pause();
//
//// Resume the upload
//uploadTask.resume();
//
//// Cancel the upload
//uploadTask.cancel();