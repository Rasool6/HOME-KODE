package com.variable.home_kode.Users.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.variable.home_kode.R;

public class AddVideoActivity extends AppCompatActivity {


    TextView txt_seletcimage;
    VideoView videoView;

    private Uri videouri;
    private static final int REQUEST_CODE = 101;
    private StorageReference videoref;
    private MediaController mediaController;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_video);
        progressDialog=new ProgressDialog(this);

        storageReference= FirebaseStorage.getInstance().getReference();
        databaseReference= FirebaseDatabase.getInstance().getReference().child("video");


        StorageReference storageRef = FirebaseStorage.getInstance().getReference();
        videoref =storageRef.child("/videos" + "/userIntro.3gp");

        txt_seletcimage=findViewById(R.id.txt_seletcimageId);
        videoView=findViewById(R.id.videoView);
        mediaController = new MediaController(this);

        txt_seletcimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openVideo();
            }
        });
    }
    public void openVideo() {
        try {
            Intent intent = new Intent();
            intent.setType("video/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Select Video"), REQUEST_CODE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        videouri = data.getData();

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                videoView.setMediaController(mediaController);
                videoView.setVideoURI(videouri);
                videoView.requestFocus();
                videoView.start();
//                videoView.setOnPreparedListener(mediaPlayer -> {
//                    mediaController.setAnchorView(videoView);
//                });
                Toast.makeText(this, "Video saved to:\n" +
                        videouri, Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Video recording cancelled.",
                        Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to record video",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getExtention(){
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(getContentResolver().getType(videouri));
    }

    public void nextvideo(View view) {
        progressDialog.show();
        if (videouri != null) {
            StorageReference uploader= storageReference.child("myVideo/"+System.currentTimeMillis()+"."+getExtention());
            uploader.putFile(videouri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    uploader.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                           // senddat(uri.toString());
                            startActivity(new Intent(AddVideoActivity.this, AddpostActivity8.class)

                                    .putExtra("videoUri",uri.toString())

                                    .putExtra("str_yardType8",getIntent().getStringExtra("str_yardType7"))
                                    .putExtra("str_checkbox_ProType8",getIntent().getStringExtra("str_checkbox_ProType7"))


                                    .putExtra("spinner_area8",getIntent().getStringExtra("spinner_area7") )
                                    .putExtra("spinner_city8",getIntent().getStringExtra("spinner_city7"))
                                    .putExtra("et_homeDetail8", getIntent().getStringExtra("et_homeDetail7"))
                                    .putExtra("str_fireAlarm_checkbox8",getIntent().getStringExtra("str_fireAlarm_checkbox7"))
                                    .putExtra("cctv8",getIntent().getStringExtra("cctv7"))
                                    .putExtra("securityGuard8",getIntent().getStringExtra("securityGuard7"))
                                    .putExtra("caretaker8",getIntent().getStringExtra("caretaker7"))
                                    .putExtra("wifi8",getIntent().getStringExtra("wifi7"))
                                    .putExtra("water8",getIntent().getStringExtra("water7"))
                                    .putExtra("gas8",getIntent().getStringExtra("gas7"))
                                    .putExtra("str_lift8",getIntent().getStringExtra("str_lift7"))
                                    .putExtra("str_fagarge8",getIntent().getStringExtra("str_fagarge7"))

                                    .putExtra("str_checkBox_sublet8",getIntent().getStringExtra("str_checkBox_sublet7"))
                                    .putExtra("str_checkBox_stall8",getIntent().getStringExtra("str_checkBox_stall7"))
                                    .putExtra("str_checkBoxd_family8",getIntent().getStringExtra("str_checkBoxd_family7"))
                                    .putExtra("str_checkBoxd_bachelor8",getIntent().getStringExtra("str_checkBoxd_bachelor7"))
                                    .putExtra("str_checkBoxd_office8",getIntent().getStringExtra("str_checkBoxd_office7"))
                                    .putExtra("str_checkBoxd_goddown8",getIntent().getStringExtra("str_checkBoxd_goddown7"))
                                    .putExtra("str_checkBoxd_garage8",getIntent().getStringExtra("str_checkBoxd_garage7"))
                                    .putExtra("str_checkBox_Commercial_Space8",getIntent().getStringExtra("str_checkBox_Commercial_Space7"))

                                    .putExtra("str_edtr_Rent8",getIntent().getStringExtra("str_edtr_Rent7"))
                                    .putExtra("str_negotiablefixed8",getIntent().getStringExtra("str_negotiablefixed7"))
                                    .putExtra("str_edtr_Sq_Feet8",getIntent().getStringExtra("str_edtr_Sq_Feet7"))
                                    .putExtra("str_edtr_contactNo8",getIntent().getStringExtra("str_edtr_contactNo7"))
                                    .putExtra("spinner_month8",getIntent().getStringExtra("spinner_month7"))
                                    .putExtra("latitude8", getIntent().getStringExtra("latitude7"))
                                    .putExtra("longitud8", getIntent().getStringExtra("longitud7"))
                                    .putExtra("str_checkBoxd_drawing_dinning8", getIntent().getStringExtra("str_checkBoxd_drawing_dinning7"))
                                    .putExtra("str_dinning8", getIntent().getStringExtra("str_dinning7"))
                                    .putExtra("str_drawing8", getIntent().getStringExtra("str_drawing7"))
                                    .putExtra("str_kitchen8", getIntent().getStringExtra("str_kitchen7"))
                                    .putExtra("houseName8", getIntent().getStringExtra("houseName7"))
                                    .putExtra("spinner_rooms8", getIntent().getStringExtra("spinner_rooms7"))
                                    .putExtra("spinner_bathrooms8", getIntent().getStringExtra("spinner_bathrooms7"))
                                    .putExtra("spinner_balcony8", getIntent().getStringExtra("spinner_balcony7"))
                                    .putExtra("spinner_floor8", getIntent().getStringExtra("spinner_floor7"))
                            );

                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    long per=(100*snapshot.getBytesTransferred())/snapshot.getTotalByteCount();
                    long per1=per;
                    progressDialog.setMessage("Uploaded:"+per+"%");
                    //     updateProgress(snapshot);

                }
            });
    }

}

    private void senddat(String videoUri) {
      //  DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("AdsPost").push();

        startActivity(new Intent(AddVideoActivity.this, AddpostActivity8.class)

                .putExtra("videoUri",videoUri)

                .putExtra("str_yardType8",getIntent().getStringExtra("str_yardType7"))
                .putExtra("str_checkbox_ProType8",getIntent().getStringExtra("str_checkbox_ProType7"))


                .putExtra("spinner_area8",getIntent().getStringExtra("spinner_area7") )
                .putExtra("spinner_city8",getIntent().getStringExtra("spinner_city7"))
                .putExtra("et_homeDetail8", getIntent().getStringExtra("et_homeDetail7"))
                .putExtra("str_fireAlarm_checkbox8",getIntent().getStringExtra("str_fireAlarm_checkbox7"))
                .putExtra("cctv8",getIntent().getStringExtra("cctv7"))
                .putExtra("securityGuard8",getIntent().getStringExtra("securityGuard7"))
                .putExtra("caretaker8",getIntent().getStringExtra("caretaker7"))
                .putExtra("wifi8",getIntent().getStringExtra("wifi7"))
                .putExtra("water8",getIntent().getStringExtra("water7"))
                .putExtra("gas8",getIntent().getStringExtra("gas7"))
                .putExtra("str_lift8",getIntent().getStringExtra("str_lift7"))
                .putExtra("str_fagarge8",getIntent().getStringExtra("str_fagarge7"))

                .putExtra("str_checkBox_sublet8",getIntent().getStringExtra("str_checkBox_sublet7"))
                .putExtra("str_checkBox_stall8",getIntent().getStringExtra("str_checkBox_stall7"))
                .putExtra("str_checkBoxd_family8",getIntent().getStringExtra("str_checkBoxd_family7"))
                .putExtra("str_checkBoxd_bachelor8",getIntent().getStringExtra("str_checkBoxd_bachelor7"))
                .putExtra("str_checkBoxd_office8",getIntent().getStringExtra("str_checkBoxd_office7"))
                .putExtra("str_checkBoxd_goddown8",getIntent().getStringExtra("str_checkBoxd_goddown7"))
                .putExtra("str_checkBoxd_garage8",getIntent().getStringExtra("str_checkBoxd_garage7"))
                .putExtra("str_checkBox_Commercial_Space8",getIntent().getStringExtra("str_checkBox_Commercial_Space7"))

                .putExtra("str_edtr_Rent8",getIntent().getStringExtra("str_edtr_Rent7"))
                .putExtra("str_negotiablefixed8",getIntent().getStringExtra("str_negotiablefixed7"))
                .putExtra("str_edtr_Sq_Feet8",getIntent().getStringExtra("str_edtr_Sq_Feet7"))
                .putExtra("str_edtr_contactNo8",getIntent().getStringExtra("str_edtr_contactNo7"))
                .putExtra("spinner_month8",getIntent().getStringExtra("spinner_month7"))
                .putExtra("latitude8", getIntent().getStringExtra("latitude7"))
                .putExtra("longitud8", getIntent().getStringExtra("longitud7"))
                .putExtra("str_checkBoxd_drawing_dinning8", getIntent().getStringExtra("str_checkBoxd_drawing_dinning7"))
                .putExtra("str_dinning8", getIntent().getStringExtra("str_dinning7"))
                .putExtra("str_drawing8", getIntent().getStringExtra("str_drawing7"))
                .putExtra("str_kitchen8", getIntent().getStringExtra("str_kitchen7"))
                .putExtra("houseName8", getIntent().getStringExtra("houseName7"))
                .putExtra("spinner_rooms8", getIntent().getStringExtra("spinner_rooms7"))
                .putExtra("spinner_bathrooms8", getIntent().getStringExtra("spinner_bathrooms7"))
                .putExtra("spinner_balcony8", getIntent().getStringExtra("spinner_balcony7"))
                .putExtra("spinner_floor8", getIntent().getStringExtra("spinner_floor7"))
        );

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddVideoActivity.this,AddpostActivity7.class));
        finish();
    }

    public void backVideo(View view) {
        startActivity(new Intent(AddVideoActivity.this,AddpostActivity7.class));
        finish();
    }

    }
