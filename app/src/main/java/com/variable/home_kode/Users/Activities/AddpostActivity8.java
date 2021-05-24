package com.variable.home_kode.Users.Activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class AddpostActivity8 extends AppCompatActivity {
    private static final int PICK_IMG = 2;
    static List<String> imagesDataBaseList;
    TextView choose,uploadbtn;
    MaterialButton nextbtn;
    DatabaseReference reference;
    int index = 0;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private int uploads = 0;
    private StorageTask mUploadTask;
    private DatabaseReference databaseReference;
    private ProgressDialog progressDialog;
    Uri imageuri;
    int CurrentImageSelect;
    SharedPreferences sharedPreferences;



            @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost8);
        reference = FirebaseDatabase.getInstance().getReference();
        sharedPreferences = getSharedPreferences(Appdata.PREFS_NAME, Context.MODE_PRIVATE);
//
        choose = findViewById(R.id.txt_seletcimageId);
        uploadbtn = findViewById(R.id.txt_uploadimageId);
        nextbtn = findViewById(R.id.nextUpBtn);
        ImageList = new ArrayList<>();
        imagesDataBaseList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("User_one");
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Uploading ..........");

        //   textView = findViewById(R.id.text);
        //   send = findViewById(R.id.upload);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //we will pick images
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(intent, PICK_IMG);


            }
        });


    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMG) {
            if (resultCode == RESULT_OK) {
                if (data.getClipData() != null)
                {
                    int count = data.getClipData().getItemCount();

                     CurrentImageSelect = 0;

                    while (CurrentImageSelect < count) {
                         imageuri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                        ImageList.add(imageuri);
                        CurrentImageSelect = CurrentImageSelect + 1;
                    }

                    //  textView.setVisibility(View.VISIBLE);
                    choose.setText("You Have Selected " + ImageList.size() + " Pictures");
                     uploadbtn.setVisibility(View.VISIBLE);
                }
                else
                    if (data.getData()!=null)
                {
                    imageuri = data.getData();
                    ImageList.add(imageuri);
                    choose.setText("You Have Selected " + ImageList.size() + " Pictures");
                    uploadbtn.setVisibility(View.VISIBLE);
                }

            }

        }


    }


    public void uploadImagebtn(View view) {
        if (imageuri!=null){


           progressDialog.show();
           progressDialog.setMessage("Please Wait ... Until the Images is properly uploaded");
           progressDialog.setCancelable(false);
        final StorageReference ImageFolder = FirebaseStorage.getInstance().getReference().child("ImageFolder");
        for (uploads = 0; uploads < ImageList.size(); uploads++) {
            Uri Image = ImageList.get(uploads);
            final StorageReference imagename = ImageFolder.child("image/" + Image.getLastPathSegment());

            imagename.putFile(ImageList.get(uploads)).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    imagename.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                         //   progressDialog.dismiss();
                            String url = String.valueOf(uri);
                            imagesDataBaseList.add(url);

                            if ( imagesDataBaseList.size()==ImageList.size()) {
                                progressDialog.dismiss();
                                nextbtn.setVisibility(View.VISIBLE);
                            } else {
                                progressDialog.show();
                            }


                        }
                    });

                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {

                }
            });
            }
        }else {
            Toast.makeText(this, "Please Select Image From Gallery Please", Toast.LENGTH_SHORT).show();
        }
    }

    public void next6(View view) {

        SendLink(imagesDataBaseList);


    }

    private void SendLink(List<String> url) {



     //   progressDialog.setCancelable(false);
        if (imageuri!=null && imagesDataBaseList.size()==ImageList.size()) {
            progressDialog.show();
            DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy, h:mm a");
            String currntdate = df.format(Calendar.getInstance().getTime());

            FirebaseAuth mAuth = FirebaseAuth.getInstance();
            DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("AdsPost").push();
            ref.child("user_id").setValue(sharedPreferences.getString(Appdata.USER_key, null));
            ref.child("posterName").setValue(sharedPreferences.getString(Appdata.userNameKey, null));
            ref.child("userImageUri").setValue(sharedPreferences.getString(Appdata.userIMGkey, null));
            ref.child("currentDate").setValue(currntdate);

//            Appdata.UID = sharedPreferences.getString(Appdata.USER_key, null);
//            Appdata.USERIMGURL = sharedPreferences.getString(Appdata.userIMGkey, null);

/////////////////
           // ref.child("videoUri").setValue(getIntent().getStringExtra("videoUri"));
            ref.child("videoUri").setValue("");

            ref.child("yardType").setValue(getIntent().getStringExtra("str_yardType7"));
            ref.child("proType").setValue(getIntent().getStringExtra("str_checkbox_ProType7"));
////////////////
            ref.child("spinner_floor").setValue(getIntent().getStringExtra("spinner_floor7"));
            ref.child("spinner_balcony").setValue(getIntent().getStringExtra("spinner_balcony7"));
            ref.child("spinner_bathrooms").setValue(getIntent().getStringExtra("spinner_bathrooms7"));
            ref.child("spinner_rooms").setValue(getIntent().getStringExtra("spinner_rooms7"));
            ref.child("houseName").setValue(getIntent().getStringExtra("houseName7"));
////////////////////
            ref.child("str_kitchen").setValue(getIntent().getStringExtra("str_kitchen7"));
            ref.child("str_drawing").setValue(getIntent().getStringExtra("str_drawin7"));
            ref.child("str_dinning").setValue(getIntent().getStringExtra("str_dinning7"));
            ref.child("checkBoxd_drawing").setValue(getIntent().getStringExtra("str_checkBoxd_drawing_dinning7"));
////////////////////////
            ref.child("longitud").setValue(getIntent().getStringExtra("longitud7"));
            ref.child("latitude").setValue(getIntent().getStringExtra("latitude7"));
////////////////////////////
            ref.child("spinner_month").setValue(getIntent().getStringExtra("spinner_month7"));
            ref.child("edtr_contactNo").setValue(getIntent().getStringExtra("str_edtr_contactNo7"));
            ref.child("str_Sq_Feet").setValue(getIntent().getStringExtra("str_edtr_Sq_Feet7"));
            ref.child("str_negotiablefixed").setValue(getIntent().getStringExtra("str_negotiablefixed7"));
            ref.child("edtr_Rent").setValue(getIntent().getStringExtra("str_edtr_Rent7"));
/////////////////////////////////
            ref.child("checkBoxd_goddown").setValue(getIntent().getStringExtra("str_checkBoxd_goddown7"));
            ref.child("checkBoxd_office").setValue(getIntent().getStringExtra("str_checkBoxd_office7"));
            ref.child("checkBoxd_bachelor").setValue(getIntent().getStringExtra("str_checkBoxd_bachelor7"));
            ref.child("checkBoxd_family").setValue(getIntent().getStringExtra("str_checkBoxd_family7"));
            ref.child("str_checkBox_stall11").setValue(getIntent().getStringExtra("str_checkBox_stall7"));
            ref.child("str_checkBox_sublet11").setValue(getIntent().getStringExtra("str_checkBox_sublet7"));
            ref.child("commercial_space").setValue(getIntent().getStringExtra("str_checkBox_Commercial_Space7"));
            ref.child("checkBoxRent_garage").setValue(getIntent().getStringExtra("str_checkBoxd_garage7"));
////////////////////////////////
            ref.child("gas").setValue(getIntent().getStringExtra("gas7"));
            ref.child("water").setValue(getIntent().getStringExtra("water7"));
            ref.child("wifi").setValue(getIntent().getStringExtra("wifi7"));
            ref.child("caretaker").setValue(getIntent().getStringExtra("caretaker7"));
            ref.child("securityGuard").setValue(getIntent().getStringExtra("securityGuard7"));
            ref.child("cctv").setValue(getIntent().getStringExtra("cctv7"));
            ref.child("str_fireAlarm").setValue(getIntent().getStringExtra("str_fireAlarm_checkbox7"));
            ref.child("str_fagarge").setValue(getIntent().getStringExtra("str_fagarge7"));
            ref.child("str_lift").setValue(getIntent().getStringExtra("str_lift7"));
//////////////////////////////////
            ref.child("homeDetail").setValue(getIntent().getStringExtra("et_homeDetail7"));
            ref.child("spinner_city").setValue(getIntent().getStringExtra("spinner_city7"));
            ref.child("area").setValue(getIntent().getStringExtra("spinner_area7"));
////////////////////////////////////
            ref.child("imageList").setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        progressDialog.dismiss();
                        Toast.makeText(AddpostActivity8.this, "Ad Uploaded Successfully..!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddpostActivity8.this, User_DashboardpActivity.class));
                        finish();

                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(AddpostActivity8.this, "Failed", Toast.LENGTH_SHORT).show();
                    }
                }
            });


        } else {
            Toast.makeText(this, "Select And Upload Images From Gallery First..!", Toast.LENGTH_SHORT).show();
           // progressDialog.show();
        }



    }


    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.show();
        builder.setMessage("Are sure to cancel this post");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(AddpostActivity8.this,User_DashboardpActivity.class));
                finish();
            }
        });
        builder.setNegativeButton("No",null);


    }

    public void backVideo(View view)
    {
        startActivity(new Intent(AddpostActivity8.this,AddpostActivity7.class));
        finish();
    }


}


