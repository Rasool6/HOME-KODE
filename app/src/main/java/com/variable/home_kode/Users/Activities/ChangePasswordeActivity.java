package com.variable.home_kode.Users.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

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
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.R;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChangePasswordeActivity extends AppCompatActivity {
    EditText email,phoneNo,password,confirmPass;
    MaterialButton signUpBtnn;
    private View parent_view;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
////////////

    ////////////
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_passworde);

        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Loading...!");

        parent_view = findViewById(android.R.id.content);
        email=findViewById(R.id.editTextTextEmailAddress);
        password=findViewById(R.id.editTextTextPassword);
        confirmPass=findViewById(R.id.editTextTextPassword2);
        signUpBtnn=findViewById(R.id.signUpBtn);



        listeners();




    }

    private void listeners() {

        signUpBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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
//                else  if (phoneNo.getText().toString().isEmpty()) {
//                    phoneNo.setError("Enter phone number");
//                    Snackbar.make(parent_view, "Enter phone number", Snackbar.LENGTH_SHORT).show();
//                    return;
//                }
                else if (password.getText().toString().isEmpty()) {
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
               }
//                else if (!password.getText().toString().matches(confirmPass.getText().toString())) {
//                    Snackbar.make(parent_view, "Password not Matched", Snackbar.LENGTH_SHORT).show();
//                    confirmPass.setError("Password not Matched");
//                    return;
//                }
                 else
                {
                    updatePassword(email.getText().toString().trim(), password.getText().toString()
                           );

                }


            }

        });
    }

    private void updatePassword(String email1, String password1) {

        progressDialog.show();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

// Get auth credentials from the user for re-authentication. The example below shows
// email and password credentials but there are multiple possible providers,
// such as GoogleAuthProvider or FacebookAuthProvider.
        AuthCredential credential = EmailAuthProvider
                .getCredential(email1, password1);

// Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(confirmPass.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        progressDialog.dismiss();
                                        senddata(user.getUid(),confirmPass.getText().toString());
                                        Toast.makeText(ChangePasswordeActivity.this, "Password Updated", Toast.LENGTH_SHORT).show();
                                        Log.d("TAG", "Password updated");
                                    } else {
                                        progressDialog.dismiss();
                                        Toast.makeText(ChangePasswordeActivity.this, "Error password not updated", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else {
                            Log.d("TAG", "Error auth failed");
                        }
                    }
                });



    }

    private void senddata(String uid, String newPass) {

        FirebaseDatabase.getInstance().getReference("Users").child(uid).child("user_password").setValue(newPass, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    Log.e("TAG", "Data Not saved");
                    progressDialog.dismiss();
                } else {
                    Toast.makeText(ChangePasswordeActivity.this, "Profile  created successfully", Toast.LENGTH_SHORT).show();
                    Log.e("ss", "Data saved successfully");
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
    @Override
    public void onBackPressed() {
        startActivity(new Intent(ChangePasswordeActivity.this,User_DashboardpActivity.class));
        finish();    }

    ////////////
}