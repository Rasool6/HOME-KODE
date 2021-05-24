package com.variable.home_kode.Users.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.variable.home_kode.GpsTracker;
import com.variable.home_kode.R;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AddpostActivity2 extends AppCompatActivity {
    EditText edtAddress;
    CheckBox checkBoxd_drawing,checkBoxd_dinning,checkBoxd_drawing_dinning,checkBoxd_kitchen;
    private View parent_view;
    TextView getCurrentAddress_btn;
    String str_drawing="";
    String str_dinning="";
    String str_kitchen="";
    String str_houseadds="";
    String str_checkBoxd_drawing_dinning="";
    String houseName_intent,spinner_rooms_intent,spinner_bathrooms_intent,spinner_balcony_intent,spinner_floor_intent;
    GpsTracker gpsTracker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost2);
        parent_view = findViewById(android.R.id.content);

        getCurrentAddress_btn=findViewById(R.id.getCurrentAddresId);
        edtAddress=findViewById(R.id.editaddress);
        checkBoxd_drawing=findViewById(R.id.drawing);
        checkBoxd_dinning=findViewById(R.id.dinning);
        checkBoxd_drawing_dinning=findViewById(R.id.drawingdinning);
        checkBoxd_kitchen=findViewById(R.id.kitchen);
         //
        houseName_intent=getIntent().getStringExtra("str_name1");
        spinner_rooms_intent=getIntent().getStringExtra("spinner_rooms1");
        spinner_bathrooms_intent=getIntent().getStringExtra("spinner_bathrooms1");
        spinner_balcony_intent=getIntent().getStringExtra("spinner_balcony1");
        spinner_floor_intent=getIntent().getStringExtra("spinner_floor1");

        getCurrentAddress_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(AddpostActivity2.this);

                builder.setTitle("Guideline");
                builder.setMessage("If you are near or inside the current house (ad),Use this option for better result");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                         gpsTracker = new GpsTracker(AddpostActivity2.this);
                        if (gpsTracker.canGetLocation()) {
                            double latitude = gpsTracker.getLatitude();
                            double longitude = gpsTracker.getLongitude();
                            try {
                                String address=getAddress(latitude,longitude);
                                edtAddress.setText(address);


                            } catch (RuntimeException e) {
                                e.printStackTrace();
                            }

                        } else {
                            gpsTracker.showSettingsAlert();
                        }

                    }
                });

                builder.setNegativeButton("No",null);
                builder.show();
            }
        });




        checkBoxcondition();


    }

    private void checkBoxcondition() {
        checkBoxd_drawing_dinning.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    checkBoxd_drawing.setChecked(false);
                    checkBoxd_dinning.setChecked(false);

                    str_checkBoxd_drawing_dinning = "Attached Dining and Drawing";

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



    }

    public void next2(View view) {
        String str_edtAddress = edtAddress.getText().toString().trim();


        if (TextUtils.isEmpty(str_edtAddress)) {
            edtAddress.setError("Enter House Address");
            Snackbar.make(parent_view, "Enter House Address", Snackbar.LENGTH_SHORT).show();
            return;
            // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

        } else {

            Geocoder coder = new Geocoder(this);
            List<Address> address;
            try {
                address = coder.getFromLocationName(str_edtAddress, 5);
                if (address == null) {
                    Log.d("address", "addresss");
                    Toast.makeText(gpsTracker, "Provide Correct Address Please", Toast.LENGTH_SHORT).show();

                } else {
                    
                Address location = address.get(0);
                location.getLatitude();
                location.getLongitude();

                double latitude = location.getLatitude();
                double longitud = location.getLongitude();
                String str_lati = String.valueOf(latitude);
                String str_longi = String.valueOf(longitud);

                startActivity(new Intent(AddpostActivity2.this, AddpostActivity3.class)
                        .putExtra("latitude2", str_lati)
                        .putExtra("longitud2", str_longi)
                        .putExtra("str_checkBoxd_drawing_dinning2", str_checkBoxd_drawing_dinning)
                        .putExtra("str_dinning2", str_dinning)
                        .putExtra("str_drawing2", str_drawing)
                        .putExtra("str_kitchen2", str_kitchen)
                        .putExtra("houseName2", houseName_intent)
                        .putExtra("spinner_rooms2", spinner_rooms_intent)
                        .putExtra("spinner_bathrooms2", spinner_bathrooms_intent)
                        .putExtra("spinner_balcony2", spinner_balcony_intent)
                        .putExtra("spinner_floor2", spinner_floor_intent)
                );

            }

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Provide Correct Address Format Please", Toast.LENGTH_SHORT).show();
                return;
            }


        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddpostActivity2.this,AddpostActivity1.class));
        finish();
    }

    public void back2(View view) {
        startActivity(new Intent(AddpostActivity2.this,AddpostActivity1.class));
        finish();
    }
    String getAddress(double latitude, double longitude) {
        if (Geocoder.isPresent()) {
            Geocoder myLocation = new Geocoder(this, Locale.getDefault());
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
