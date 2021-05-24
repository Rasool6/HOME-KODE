package com.variable.home_kode.Users.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AddspostModel implements Serializable {

    public String user_id;
    public String post_id;
    public String posterName;
    public String spinner_floor;
    public String spinner_balcony;
    public String spinner_bathrooms;
    public String spinner_rooms;
    public String houseName;
    public String str_kitchen;
    public String str_drawing;
    public String str_draw_dinn;
    public String str_dinning;
    public String longitud;
    public String latitude;
    public String spinner_month;
    public String edtr_contactNo;
    public String str_Sq_Feet;
    public String str_negotiablefixed;

    public String edtr_Rent;
    public String checkBoxd_garage;
    public String checkBoxd_goddown;
    public String checkBoxd_office;
    public String checkBoxd_bachelor;
    public String checkBoxd_family;
    public String gas;
    public String water;
    public String wifi;
    public String caretaker;
    public String securityGuard;
    public String cctv;
    public String homeDetail;
    public String str_checkBox_stall11;
    public String str_checkBox_sublet11;
    public String str_fagarge;
    public String str_lift;
    public String spinner_city;
    public String address;
    public String area;
    public String currentDate;
    public String yardType;
    public String proType;
    public String alarm;
    public String commercial;
    public String distanceInKm;
    public String userImageUri;
    public String videoUri;

    public List<String> imageList = new ArrayList<>();

    public AddspostModel(String user_id, String post_id, String posterName, String spinner_floor,
                         String spinner_balcony, String spinner_bathrooms, String spinner_rooms,
                         String houseName, String str_kitchen, String str_drawing, String str_draw_dinn,
                         String str_dinning, String longitud, String latitude, String spinner_month,
                         String edtr_contactNo, String str_Sq_Feet, String str_negotiablefixed,
                         String edtr_Rent, String checkBoxd_garage, String checkBoxd_goddown,
                         String checkBoxd_office, String checkBoxd_bachelor, String checkBoxd_family,
                         String gas, String water, String wifi, String caretaker, String securityGuard,
                         String cctv, String homeDetail, String str_checkBox_stall11,
                         String str_checkBox_sublet11, String str_fagarge, String str_lift,
                         String spinner_city, String address, String area, String currentDate, String yardType,
                         String proType,
                         String alarm,
                         String commercial, String distanceInKm, String userImageUri, String videoUri) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.posterName = posterName;
        this.spinner_floor = spinner_floor;
        this.spinner_balcony = spinner_balcony;
        this.spinner_bathrooms = spinner_bathrooms;
        this.spinner_rooms = spinner_rooms;
        this.houseName = houseName;
        this.str_kitchen = str_kitchen;
        this.str_drawing = str_drawing;
        this.str_draw_dinn = str_draw_dinn;
        this.str_dinning = str_dinning;
        this.longitud = longitud;
        this.latitude = latitude;
        this.spinner_month = spinner_month;
        this.edtr_contactNo = edtr_contactNo;
        this.str_Sq_Feet = str_Sq_Feet;
        this.str_negotiablefixed = str_negotiablefixed;
        this.edtr_Rent = edtr_Rent;
        this.checkBoxd_garage = checkBoxd_garage;
        this.checkBoxd_goddown = checkBoxd_goddown;
        this.checkBoxd_office = checkBoxd_office;
        this.checkBoxd_bachelor = checkBoxd_bachelor;
        this.checkBoxd_family = checkBoxd_family;
        this.gas = gas;
        this.water = water;
        this.wifi = wifi;
        this.caretaker = caretaker;
        this.securityGuard = securityGuard;
        this.cctv = cctv;
        this.homeDetail = homeDetail;
        this.str_checkBox_stall11 = str_checkBox_stall11;
        this.str_checkBox_sublet11 = str_checkBox_sublet11;
        this.str_fagarge = str_fagarge;
        this.str_lift = str_lift;
        this.spinner_city = spinner_city;
        this.address = address;
        this.area = area;
        this.currentDate = currentDate;
        this.yardType = yardType;
        this.proType = proType;

        this.alarm = alarm;
        this.commercial = commercial;
        this.distanceInKm = distanceInKm;
        this.userImageUri = userImageUri;
        this.videoUri = videoUri;
    }

    public AddspostModel(String user_id, String post_id, String posterName, String spinner_floor,
                         String spinner_balcony, String spinner_bathrooms, String spinner_rooms,
                         String houseName, String str_kitchen, String str_drawing, String str_draw_dinn,
                         String str_dinning,
                         String longitud, String latitude, String spinner_month, String edtr_contactNo,
                         String str_Sq_Feet, String str_negotiablefixed, String edtr_Rent, String checkBoxd_garage,
                         String checkBoxd_goddown, String checkBoxd_office, String checkBoxd_bachelor,
                         String checkBoxd_family, String gas, String water, String wifi, String caretaker,
                         String securityGuard, String cctv, String homeDetail, String str_checkBox_stall11,
                         String str_checkBox_sublet11, String str_fagarge, String str_lift, String spinner_city,
                         String address, String area,
                         String currentDate, String yardType,
                         String proType, String alarm,
                         String commercial, String distanceInKm, String userImageUri, String videoUri, List<String> imageList) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.posterName = posterName;
        this.spinner_floor = spinner_floor;
        this.spinner_balcony = spinner_balcony;
        this.spinner_bathrooms = spinner_bathrooms;
        this.spinner_rooms = spinner_rooms;
        this.houseName = houseName;
        this.str_kitchen = str_kitchen;
        this.str_drawing = str_drawing;
        this.str_draw_dinn = str_draw_dinn;
        this.str_dinning = str_dinning;
        this.longitud = longitud;
        this.latitude = latitude;
        this.spinner_month = spinner_month;
        this.edtr_contactNo = edtr_contactNo;
        this.str_Sq_Feet = str_Sq_Feet;
        this.str_negotiablefixed = str_negotiablefixed;
        this.edtr_Rent = edtr_Rent;
        this.checkBoxd_garage = checkBoxd_garage;
        this.checkBoxd_goddown = checkBoxd_goddown;
        this.checkBoxd_office = checkBoxd_office;
        this.checkBoxd_bachelor = checkBoxd_bachelor;
        this.checkBoxd_family = checkBoxd_family;
        this.gas = gas;
        this.water = water;
        this.wifi = wifi;
        this.caretaker = caretaker;
        this.securityGuard = securityGuard;
        this.cctv = cctv;
        this.homeDetail = homeDetail;
        this.str_checkBox_stall11 = str_checkBox_stall11;
        this.str_checkBox_sublet11 = str_checkBox_sublet11;
        this.str_fagarge = str_fagarge;
        this.str_lift = str_lift;
        this.spinner_city = spinner_city;
        this.address = address;
        this.area = area;
        this.currentDate = currentDate;
        this.yardType = yardType;
        this.proType = proType;
        this.alarm = alarm;
        this.commercial = commercial;
        this.distanceInKm = distanceInKm;
        this.userImageUri = userImageUri;
        this.videoUri = videoUri;
        this.imageList = imageList;


    }

    public AddspostModel(String user_id, String post_id, String posterName, String longitud, String spinner_month, String edtr_Rent, String spinner_city) {
        this.user_id = user_id;
        this.post_id = post_id;
        this.posterName = posterName;
        this.longitud = longitud;
        this.spinner_month = spinner_month;
        this.edtr_Rent = edtr_Rent;
        this.spinner_city = spinner_city;


    }
}

