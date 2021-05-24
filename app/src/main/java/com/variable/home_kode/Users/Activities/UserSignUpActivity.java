package com.variable.home_kode.Users.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Model.UserModel;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserSignUpActivity extends AppCompatActivity {

    EditText name,email,phoneNo,password,confirmPass;
    MaterialButton signUpBtnn;
    private View parent_view;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_sign_up);
        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...!");

        parent_view = findViewById(android.R.id.content);
        name=findViewById(R.id.editTextTextPersonName);
        email=findViewById(R.id.editTextTextEmailAddress);
        phoneNo=findViewById(R.id.phn_et);
        password=findViewById(R.id.editTextTextPassword);
        confirmPass=findViewById(R.id.editTextTextPassword2);
        signUpBtnn=findViewById(R.id.signUpBtn);



        listeners();




    }

    private void listeners() {

        signUpBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.getText().toString().isEmpty())  {
                    name.setError("Enter Name");
                    Snackbar.make(parent_view, "Enter Name", Snackbar.LENGTH_SHORT).show();
                    return;
                    // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else
                if (!(isValidName(name.getText().toString().trim() )))  {
                    name.setError("Only letters allowed..!");
                    Snackbar.make(parent_view, "Invalid state", Snackbar.LENGTH_SHORT).show();
                    return;
                    // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

                } else
                if (email.getText().toString().isEmpty()) {
                    email.setError("Enter email address");
                    Snackbar.make(parent_view, "Enter email address", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                else
                if (!isValidEmailId(email.getText().toString().trim())) {
                    email.setError("Enter valid email address");
                    Snackbar.make(parent_view, "Enter valid email address", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                else  if (phoneNo.getText().toString().isEmpty()) {
                    phoneNo.setError("Enter phone number");
                    Snackbar.make(parent_view, "Enter phone number", Snackbar.LENGTH_SHORT).show();
                    return;
                }  else if (password.getText().toString().isEmpty()) {
                    password.setError("Enter password");
                    Snackbar.make(parent_view, "Enter password", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                else if (password.getText().toString().trim().length() < 8) {
                    password.setError("Password should be 8 or more");
                    Snackbar.make(parent_view, "Password must be greater than 8..!", Snackbar.LENGTH_SHORT).show();
                    return;
                } else
                if (!(isValidPassword(password.getText().toString().trim()))){
                    Snackbar.make(parent_view, "InValid)", Snackbar.LENGTH_SHORT).show();
                    password.setError("InValid (start-end with any string,special char,digit,upper lower letter and allowing  atleast 8 characters");
                    return;
                }
                else
                if (confirmPass.getText().toString().isEmpty()) {
                    confirmPass.setError("Enter password again");
                    Snackbar.make(parent_view, "Enter password again", Snackbar.LENGTH_SHORT).show();
                    return;
                } else if (!password.getText().toString().matches(confirmPass.getText().toString())) {
                    Snackbar.make(parent_view, "Password not Matched", Snackbar.LENGTH_SHORT).show();
                    confirmPass.setError("Password not Matched");
                    return;
                } else
                {
                    signUp(email.getText().toString().trim(), password.getText().toString(), name.getText().toString().trim(),
                          phoneNo.getText().toString());

                }


            }

        });
    }

    private void signUp(String email, String password, String name, String phoneNo) {

        progressDialog.show();

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        final String TAG="";
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getInstance().getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                // email sent
                                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                                senddata(currentUser.getUid(),
                                                        name,
                                                        email,
                                                        phoneNo,
                                                        password

                                                );
                                                // after email is sent just logout the user and finish this activity
//                                                                       FirebaseAuth.getInstance().signOut();

                                            } else {
                                                // email not sent, so display message and restart the activity or do whatever you wish to do

                                                //restart this activity
                                                overridePendingTransition(0, 0);
                                                finish();
                                                overridePendingTransition(0, 0);
                                                startActivity(getIntent());

                                            }

                                        }
                                    });


//                            FirebaseUser user = mAuth.getCurrentUser();

                        } else {
                            // If sign in fails, display a message to the user.
                            //progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(UserSignUpActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });



    }

    private void senddata(String uid, String name, String email, String phoneNo, String password) {
        UserModel userModel =new UserModel(name,email,phoneNo,password,"","","1");
        FirebaseDatabase.getInstance().getReference("Users").child(uid).setValue(userModel, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG", "Data Not saved");
                    progressDialog.dismiss();
                    // progressBar.setVisibility(View.GONE);

                } else {
                    Toast.makeText(UserSignUpActivity.this, "Profile  created successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UserSignUpActivity.this, UserLoginActivity.class));

                    Log.e("ss", "Data saved successfully");
//                    progressDialog.dismiss();
                    progressDialog.dismiss();

                }
            }
        });

    }


    private boolean isValidEmailId(String email) {
      /*  return Pattern.compile("^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]{1}|[\\w-]{2,}))@"
                + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])){1}|"
                + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$").matcher(email).matches(); */

        //    return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
        String emailinput = email;

        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (emailinput.matches(emailPattern)) {
//            Toast.makeText(getApplicationContext(), "valid email address", Toast.LENGTH_SHORT).show();
            return true;
        } else {
//            Toast.makeText(getApplicationContext(), "Invalid email address", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    /////
    private boolean isValidPassword(String password) {


        Pattern pattern;
        Matcher matcher;
//          ^                 # start-of-string
//                (?=.*[0-9])       # a digit must occur at least once
//                (?=.*[a-z])       # a lower case letter must occur at least once
//                (?=.*[A-Z])       # an upper case letter must occur at least once
//                (?=.*[@#$%^&+=])  # a special character must occur at least once you can replace with your special characters
//                (?=\\S+$)         # no whitespace allowed in the entire string
//                .{4,}             # anything, at least six places though
//        $                 # end-of-string

        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();


    }

    ////////////
    private boolean isValidName(String name) {

        Pattern pattern;
        Matcher matcher;

        //                (?=.*[a-z])       # a lower case letter must occur at least once
        //                (?=.*[A-Z])       # an upper case letter must occur at least once


        final String PASSWORD_PATTERN = "^[a-zA-Z ]+$";

        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(name);

        return matcher.matches();


    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserSignUpActivity.this,UserLoginActivity.class));
        finish();
    }
}