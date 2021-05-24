package com.variable.home_kode.Users.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Model.UserModel;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    CircleImageView iamgeProfile;
    EditText name,phoneNo,password,confirmPass;
    Spinner cityName;
    MaterialButton signUpBtnn;
    private View parent_view;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    ProgressDialog progressDialog1;
    Uri filePath;
    TextView uploadImge;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...!");
        progressDialog1=new ProgressDialog(this);
        progressDialog1.setMessage("Loading...!");

        parent_view = findViewById(android.R.id.content);
        iamgeProfile = findViewById(R.id.iamgeProfile);
        uploadImge = findViewById(R.id.uploadImgeId);
        name=findViewById(R.id.editTextTextPersonName);
        cityName=findViewById(R.id.editTextTextEmailAddress);
        phoneNo=findViewById(R.id.phn_et);
//        password=findViewById(R.id.editTextTextPassword);
//        confirmPass=findViewById(R.id.editTextTextPassword2);
        signUpBtnn=findViewById(R.id.signUpBtn);
        String str_name=getIntent().getStringExtra("name");
        String str_user_phone=getIntent().getStringExtra("user_phone");
             name.setText(str_name);
             phoneNo.setText(str_user_phone);


        listeners();
    }
    private void listeners() {



        uploadImge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.setTitle("File Uploader");
                progressDialog.show();

                FirebaseStorage firebaseStorage = FirebaseStorage.getInstance();
                final StorageReference storageReference = firebaseStorage.getReference("Image1" + new Random().nextInt(50));
                storageReference.putFile(filePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                DatabaseReference dbRef = firebaseDatabase.getReference();
                                //UserModel categoryModel = new UserModel(
                                        uri.toString();
                              //  );
                                //     addCategory(categoryModel);
                                DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(Appdata.UID);
                                databaseReference.child("userImageUri").setValue(uri.toString())
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()){
                                            progressDialog.dismiss();
                                            Toast.makeText(EditProfileActivity.this, "Image Uploaded Successfully..!", Toast.LENGTH_SHORT).show();
                                        }else {

                                        }
                                    }
                                });

                            }
                        });

                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                        float percent = (100 * snapshot.getBytesTransferred()) / snapshot.getTotalByteCount();
                        progressDialog.setMessage("Uploaded\t" + (int) percent + "%");
                    }
                });
            }
        });
iamgeProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(Intent.createChooser(intent, "Select image"), 1);

            }
        });

        signUpBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty())  {
                    name.setError("Enter Name");
                    Snackbar.make(parent_view, "Enter Name", Snackbar.LENGTH_SHORT).show();
                    return;

                    //    Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else

                if (cityName.getSelectedItem().toString().equals("Select City")) {
//                    cityName.setError("Enter email address");
                    Snackbar.make(parent_view, "Enter email address", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                else  if (phoneNo.getText().toString().isEmpty()) {
                    phoneNo.setError("Enter phone number");
                    Snackbar.make(parent_view, "Enter phone number", Snackbar.LENGTH_SHORT).show();
                    return;
                }

                else
                {
                    signUp(cityName.getSelectedItem().toString(),  name.getText().toString().trim(),
                            phoneNo.getText().toString());

                }


            }

        });
    }

    private void signUp(String cityName1, String name1, String phoneNo1) {
        progressDialog1.show();
        DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReference().child("Users").child(Appdata.UID);
        databaseReference.child("user_Name").setValue(name1);
        databaseReference.child("cityName").setValue(cityName1);
        databaseReference.child("user_phone").setValue(phoneNo1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()){
                   progressDialog1.dismiss();
                   Toast.makeText(EditProfileActivity.this, "Profile data Updated Successfully..!", Toast.LENGTH_SHORT).show();
               }else {
                   progressDialog1.dismiss();
                   Toast.makeText(EditProfileActivity.this, "Profile data not Updated Successfully..!", Toast.LENGTH_SHORT).show();

               }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            filePath = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(filePath);
                bitmap = BitmapFactory.decodeStream(inputStream);
                iamgeProfile.setImageBitmap(bitmap);


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

public void updateEmail(){

//    auth.signInWithEmailAndPassword("currentEmail","currentPassword")
//            .addOnCompleteListener(this) { task ->
//        if (task.isSuccessful) {
//            // Sign in success now update email
//            auth.currentUser!!.updateEmail(newEmail)
//                    .addOnCompleteListener{ task ->
//                if (task.isSuccessful) {
//                    // email update completed
//                }else{
//                    // email update failed
//                }
//            }
//        } else {
//            // sign in failed
//        }
//    }
//    firebase.auth()
//            .signInWithEmailAndPassword('you@domain.com', 'correcthorsebatterystaple')
//            .then(function(userCredential) {
//        userCredential.user.updateEmail('newyou@domain.com')
//    })
}

    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditProfileActivity.this,User_DashboardpActivity.class));
        finish();
    }


}