package com.variable.home_kode.Users.Model;

public class UserModel {

   public String user_id;
   public String user_Name;
   public String user_email;
    public String user_phone;
    public String user_password;
    public String cityName;
    public String userImageUri;
    public String loginStatus;

    ////////fb\\\\
//    public String fbid;
//    public String fbuserid;
//    public String fbUName;
//    public String fbUIMg;
//
//    public UserModel(String fbuserid, String fbUName, String fbUIMg) {
//        this.fbuserid = fbuserid;
//        this.fbUName = fbUName;
//        this.fbUIMg = fbUIMg;
//    }

    public UserModel(String userImageUri) {

        this.userImageUri = userImageUri;
    }

    public UserModel(String user_id, String user_Name, String user_email, String user_phone, String user_password,String cityName,String userImageUri,String loginStatus) {
        this.user_id = user_id;
        this.user_Name = user_Name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_password = user_password;
        this.cityName =   cityName;
        this.userImageUri =  userImageUri;
        this.loginStatus =   loginStatus;
    }

    public UserModel(String user_Name, String user_email, String user_phone, String user_password,String cityName,String userImageUri,String loginStatus) {
        this.user_Name = user_Name;
        this.user_email = user_email;
        this.user_phone = user_phone;
        this.user_password = user_password;
        this.cityName =   cityName;
        this.userImageUri =  userImageUri;
        this.loginStatus =   loginStatus;

    }
}
