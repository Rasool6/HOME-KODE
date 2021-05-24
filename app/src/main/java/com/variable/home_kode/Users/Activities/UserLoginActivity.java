package com.variable.home_kode.Users.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.variable.home_kode.Appdata;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Model.UserModel;

public class UserLoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

      CallbackManager callbackManager;
    // AccessTokenTracker accessTokenTracker;
    /*new code*/
    // You can change to any Value just be unique
    private static final int RC_SIGN_IN = 1001;
    private static final String TAG = "LoginActivity";
    EditText email, passowrd;
    MaterialButton loginBtnn;
    //   LoginButton facebookbtn;
    // FirebaseAuth.AuthStateListener authStateListener;
    FirebaseAuth mAuth;
    ProgressDialog progressDialog;
    SignInButton googleBtn;
    LoginButton facebookLoginButton;
    GoogleSignInClient mGoogleSignInClient;
    private GoogleApiClient mGoogleApiClient;
    //    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private CallbackManager mCallbackManager;
///
SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        sharedPreferences=getSharedPreferences(Appdata.PREFS_NAME, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        mAuth = FirebaseAuth.getInstance();

        //this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...!");
        FacebookSdk.sdkInitialize(this);

        mCallbackManager = CallbackManager.Factory.create();
//        mAuth = FirebaseAuth.getInstance();
//        mAuthListener = new FirebaseAuth.AuthStateListener() {
//            @Override
//            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                FirebaseUser user = firebaseAuth.getCurrentUser();
//                if (user != null) {
//                    // User is signed in
//                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
//                } else {
//                    // User is signed out
//                    Log.d(TAG, "onAuthStateChanged:signed_out");
//                }
//            }
//        };
//        LoginButton facebookLoginButton = findViewById(R.id.google);
//        SignInButton mGoogleSignInButton = findViewById(R.id.facebook);
        processReq();
        email = findViewById(R.id.editTextTextEmailAddress);
        googleBtn = findViewById(R.id.google);
        facebookLoginButton = findViewById(R.id.facebook);

        passowrd = findViewById(R.id.passwordID);
        loginBtnn = findViewById(R.id.loginbtn);

        callbackManager= CallbackManager.Factory.create();
        listeners();
        facebookLoginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handlerToken(loginResult.getAccessToken());
                Log.d("Success" ,"Success\t"+loginResult);
//                GoogleSignIn.getClient(
//                        UserLoginActivity.this,
//                        new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
//                ).signOut();
//
//                sendGooglinfo(loginResult);
            }

            @Override
            public void onCancel() {
                Log.d("TAG" ,"Cancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d("onError" ,"onError\t"+error.toString());

            }

        });
//          authStateListener=new FirebaseAuth.AuthStateListener() {
//              @Override
//              public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//                  FirebaseUser user = firebaseAuth.getCurrentUser();
//                  if (user != null) {
//                      // User is signed in
//                   //   getMoreData(user);
//                      Log.d("TAG", "onAuthStateChanged:signed_in:" + user.getUid());
//                  } else {
//                      // User is signed out
//                   //   getMoreData(null);
//                      Log.d("TAG", "onAuthStateChanged:signed_out");
//                  }
//              }
//          };
//          accessTokenTracker=new AccessTokenTracker() {
//              @Override
//              protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
//                     if (currentAccessToken==null){
//                         mAuth.signOut();
//                     }
//              }
//          };

    }

    private void handlerToken(AccessToken accessToken) {
        Log.d("TAG", "handlerToken: "+accessToken);
        AuthCredential fcredential= FacebookAuthProvider.getCredential(accessToken.getToken());
        if (mAuth.getCurrentUser() != null) {
            mAuth.signInWithCredential(fcredential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(UserLoginActivity.this, "facebook login Successfully..!", Toast.LENGTH_SHORT).show();
                        FirebaseUser user1 = mAuth.getCurrentUser();
                         sendGooglinfo(user1);
                     //   getMoreData(user1);

                    } else {
                        Toast.makeText(UserLoginActivity.this, "Failed to login with facebook..!", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(UserLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else
        {
            mAuth.signInWithCredential(fcredential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendGooglinfo(authResult.getUser());
                }
            });
        }
    }

//    private void getMoreData(FirebaseUser user1) {
//
//        if ((user1 != null)) {
//            Appdata.UID=user1.getUid();
//            String userId=user1.getUid();
//            String userName=user1.getDisplayName();
//
//            Appdata.UserName=user1.getDisplayName();
//            Appdata.USERIMGURL=user1.getPhotoUrl().toString();
//
//            UserModel userModel =new UserModel(Appdata.UserName,"","","", Appdata.USERIMGURL);
//
//            FirebaseDatabase.getInstance().getReference().child("Users").child(userId).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
//                @Override
//                public void onSuccess(Void aVoid) {
//                    Toast.makeText(UserLoginActivity.this, "Info Added..!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(UserLoginActivity.this,User_DashboardpActivity.class));
//                     finish();
//                }
//            }).addOnFailureListener(new OnFailureListener() {
//                @Override
//                public void onFailure(@NonNull Exception e) {
//                   // Toast.makeText(UserLoginActivity.this, "", Toast.LENGTH_SHORT).show();
//                }
//            });
//
//
//        }else {
//
//        }
//
//
//    }

    private void processReq() {
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }

    private void listeners() {


//        facebookLoginButton.setReadPermissions("email", "public_profile", "user_birthday");
//        facebookLoginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
//                firebaseAuthWithFacebook(loginResult.getAccessToken());
////                authWithFacebook(loginResult.getAccessToken());
//            }
//
//            @Override
//            public void onCancel() {
//                Toast.makeText(UserLoginActivity.this, "Succes", Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Toast.makeText(UserLoginActivity.this, "Succes", Toast.LENGTH_SHORT).show();
//            }
//        });

//        AccessToken accessToken = AccessToken.getCurrentAccessToken();
//        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
//          facebookLoginButton.setOnClickListener(new View.OnClickListener() {
//              @Override
//              public void onClick(View v) {
//                  LoginManager.getInstance().logInWithReadPermissions(UserLoginActivity.this, Arrays.asList("public_profile"));
//                  LoginManager.getInstance().retrieveLoginStatus(UserLoginActivity.this, new LoginStatusCallback() {
//                      @Override
//                      public void onCompleted(AccessToken accessToken) {
//                          // User was previously logged in, can log them in directly here.
//                          // If this callback is called, a popup notification appears that says
//                          // "Logged in as <User Name>"
//                          authWithFacebook(accessToken);
//                      }
//                      @Override
//                      public void onFailure() {
//                          Log.d("errro","error");
//                          // No access token could be retrieved for the user
//                      }
//                      @Override
//                      public void onError(Exception exception) {
//                          Log.d("eoor",exception.getMessage());
//                          // An error occurred
//                      }
//                  });
//              }
//          });



        googleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

//        googleBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                FirebaseAuth.getInstance().signOut();
////                LoginManager.getInstance().logOut();
//                progressLogin();
//            }
//        });
        ////
        loginBtnn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (email.getText().toString().isEmpty()) {
                    email.setError("Enter valid email");
                    // Toast.makeText(this, "Field is Empty", Toast.LENGTH_SHORT).show();
                } else if (passowrd.getText().toString().isEmpty()) {
                    passowrd.setError("Enter password");
                    //  Toast.makeText(this, "Field is Empty", Toast.LENGTH_SHORT).show();

                } else {


                    loginUser(email.getText().toString().trim().toLowerCase(), passowrd.getText().toString().trim());


                }
            }
        });

    }

    private void progressLogin() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 101);
    }

    private void loginUser(String email, String password) {
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information

                            FirebaseUser user2 = mAuth.getCurrentUser();
                            if (user2.isEmailVerified()) {
                                Appdata.UID = user2.getUid();
                                FirebaseDatabase.getInstance().getReference("Users").orderByKey().equalTo(user2.getUid()).addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                        for (DataSnapshot snapshot1:snapshot.getChildren()) {



                                            Appdata.UID=snapshot1.getKey();
                                            Appdata.UserName=snapshot1.child("user_Name").getValue(String.class);
                                            Appdata.USERIMGURL=snapshot1.child("userImageUri").getValue(String.class);
                                            Appdata.loginStatus=snapshot1.child("loginStatus").getValue(String.class);
                                            Appdata.user_email=snapshot1.child("user_email").getValue(String.class);
                                            /////////////
                                            editor.putString(Appdata.USER_key,Appdata.UID );
                                            editor.putString(Appdata.userNameKey,Appdata.UserName );
                                            editor.putString(Appdata.EMAILkey,Appdata.user_email);
                                            editor.putString(Appdata.LOGINStatus,Appdata.loginStatus);
                                            editor.putBoolean(Appdata.USER_LOGIN,true);
                                            editor.commit();
                                            //////////////
                                            startActivity(new Intent(UserLoginActivity.this, User_DashboardpActivity.class));
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {

                                    }
                                });
//                                sendGooglinfo(user2);
                                //  fetchUserData(id);
//                                startActivity(new Intent(UserLoginActivity.this, User_DashboardpActivity.class));
//                                finish();


                            } else {
                                progressDialog.dismiss();
                                //  progressBar.setVisibility(View.GONE);
                                Toast.makeText(UserLoginActivity.this, "Please Verified your email first", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(UserLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            //  progressBar.setVisibility(View.GONE);
                            progressDialog.dismiss();
                            //   WaitDialog.hideDialog();
                        }

                        // ...
                    }
                });


    }
//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
////             fb login data code
//        ///////////////////////////////
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//
//        if (requestCode == RC_SIGN_IN) {
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
//                Toast.makeText(this, "firebaseAuthWithGoogle", Toast.LENGTH_SHORT).show();
//                authWithGoogle(account);
////                firebaseAuthWithGoogle(account.getIdToken());
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
//                Log.w("TAG", "Google sign in failed", e);
//            }
//        } else {
//            callbackManager.onActivityResult(requestCode, resultCode, data);
//
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//             fb login data code
           callbackManager.onActivityResult(requestCode, resultCode, data);
        ///////////////////////////////
        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN ) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d("TAG", "firebaseAuthWithGoogle:" + account.getId());
                Toast.makeText(this, "firebaseAuthWithGoogle", Toast.LENGTH_SHORT).show();
                authWithGoogle(account);
//                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show();
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        mAuth.signOut();
        progressDialog.show();
        AuthCredential credentialg = GoogleAuthProvider.getCredential(idToken, null);
//        if (mAuth.getCurrentUser() != null) {
        mAuth.signInWithCredential(credentialg)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            progressDialog.dismiss();
                            FirebaseUser user3 = mAuth.getCurrentUser();
                            //  Appdata.UID=user3.getUid();
                            sendGooglinfo(user3);

                            //  updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
                            progressDialog.dismiss();
                            //   updateUI(null);
                        }
                    }
                });
//        } else {
//            mAuth.signInWithCredential(credentialg);
//        }
    }

    private void sendGooglinfo(FirebaseUser user3) {
        progressDialog.show();
        Appdata.UID = user3.getUid();
        Appdata.UserName = user3.getDisplayName();
        Appdata.USERIMGURL = user3.getPhotoUrl().toString();
        String emailg = user3.getEmail();
        String guid = user3.getUid();

        UserModel userModel = new UserModel(Appdata.UserName, emailg, "", "","", Appdata.USERIMGURL,"2");
        FirebaseDatabase.getInstance().getReference("Users").child(guid).setValue(userModel).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressDialog.dismiss();
                editor.putString(Appdata.USER_key,Appdata.UID );
                editor.putString(Appdata.userIMGkey,Appdata.USERIMGURL);
                editor.putString(Appdata.userNameKey,Appdata.UserName );
                editor.putString(Appdata.LOGINStatus,Appdata.loginStatus);
                editor.putBoolean(Appdata.USER_LOGIN,true);
                editor.commit();
                Toast.makeText(UserLoginActivity.this, "You are login Successfully...!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(UserLoginActivity.this, User_DashboardpActivity.class));
              //  startActivity(new Intent(UserLoginActivity.this, AddpostActivity8.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(UserLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }

    public void registerbtn(View view) {
        startActivity(new Intent(UserLoginActivity.this, UserSignUpActivity.class));
    }

    private void authWithFacebook(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().linkWithCredential(credential)
                    .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            sendGooglinfo(authResult.getUser());
                    //        Toast.makeText(UserLoginActivity.this, "Logged IN", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "onFailure: " + e.getMessage());
                            mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    sendGooglinfo(authResult.getUser());

                            //        Toast.makeText(UserLoginActivity.this, "Logged IN", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
        } else {
            mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendGooglinfo(authResult.getUser());

                }
            });
        }
    }

    private void authWithGoogle(final GoogleSignInAccount acct) {
        progressDialog.show();
        Log.d(TAG, "firebaseAuthWithGooogle:" + acct.getId());
        final AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        if (mAuth.getCurrentUser() != null) {
            mAuth.getCurrentUser().linkWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendGooglinfo(authResult.getUser());
              //      Toast.makeText(UserLoginActivity.this, "Logged IN", Toast.LENGTH_LONG).show();
                }
            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            e.printStackTrace();
                            Log.e(TAG, "onFailure: " + e.getMessage());
                            mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    sendGooglinfo(authResult.getUser());
                          //          Toast.makeText(UserLoginActivity.this, "Logged IN", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
        } else {
            mAuth.signInWithCredential(credential).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    sendGooglinfo(authResult.getUser());

                }
            });


        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        // An unresolvable error has occurred and Google APIs (including Sign-In) will not
        // be available.
        Log.d(TAG, "onConnectionFailed:" + connectionResult);
        progressDialog.dismiss();
        Toast.makeText(this, "Google Play Services error.", Toast.LENGTH_SHORT).show();
    }
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    private void firebaseAuthWithFacebook(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        final AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(UserLoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        } else {

                           sendGooglinfo(task.getResult().getUser());
                        }
                    }
                });
    }
//    @Override
//    protected void onStart() {
//        super.onStart();
//      //  mAuth.addAuthStateListener(authStateListener);
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//
//        if (currentUser!=null){
//            startActivity(new Intent(UserLoginActivity.this,User_DashboardpActivity.class));
//            finish();
//        }
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        if (authStateListener !=null){
//            mAuth.removeAuthStateListener(authStateListener);
//        }
//    }
public void forgotPassbtn1(View view) {
    startActivity(new Intent(UserLoginActivity.this,ForgotPasswordActivity.class));
    finish();
}
}