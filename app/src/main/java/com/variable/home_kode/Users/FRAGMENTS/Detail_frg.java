package com.variable.home_kode.Users.FRAGMENTS;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.AdsDetailActivity;


public class Detail_frg extends Fragment {

    TextView texhouseName,txt_rooms,txt_bathrooms,txt_floors,txt_balcony,
            txtDrawing,txtdining,txtkitchen,txtDra_din,txtAddress,
            txtrentValue,txtwidthValue,txtavailbleMonthValue,txtcontactValue,
            txtFamily,txtbachelor,txtOffice,txtGodwon,txtGaragreForRent,txtstall,txtSublet,
            txtCCTV,txtwater,txtsecurity,txtwifi,txtcaretaker,txtgas,txtelevater,txtgarageF,
            txtdetail,alarmtxt,areatxt,citytxt,commercialtxt,propertyTypetxt,yardTypetxt;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_detail_frg, container, false);


        texhouseName=view.findViewById(R.id.houseNmeValue);

        alarmtxt=view.findViewById(R.id.alarmValue);
        areatxt=view.findViewById(R.id.areaValue);
        citytxt=view.findViewById(R.id.cityValue);
        commercialtxt=view.findViewById(R.id.commercialValue);
        propertyTypetxt=view.findViewById(R.id.propertyType);
        yardTypetxt=view.findViewById(R.id.yardType);


        txt_balcony=view.findViewById(R.id.noOfBalcony);
        txt_bathrooms=view.findViewById(R.id.noOfBathrooms);
        txt_floors=view.findViewById(R.id.noOfFloor);
        txt_rooms=view.findViewById(R.id.noOfRooms);
        txtDrawing=view.findViewById(R.id.drawingValue);
        txtdining=view.findViewById(R.id.dinningvalue);
        txtkitchen=view.findViewById(R.id.kitchenValue);
        txtDra_din=view.findViewById(R.id.dra_DinValue);
        txtAddress=view.findViewById(R.id.addressValue);


        txtrentValue=view.findViewById(R.id.rentValue);
        txtwidthValue=view.findViewById(R.id.widthValue);
        txtavailbleMonthValue=view.findViewById(R.id.availbleMonthValue);
        txtcontactValue=view.findViewById(R.id.contactValue);

        txtFamily=view.findViewById(R.id.familyValue);
        txtbachelor=view.findViewById(R.id.bacheloValue);
        txtOffice=view.findViewById(R.id.officeValue);
        txtGodwon=view.findViewById(R.id.godownValue);
        txtGaragreForRent=view.findViewById(R.id.garageForRentValue);
        txtstall=view.findViewById(R.id.stallValue);
        txtSublet=view.findViewById(R.id.subletValue);

        txtCCTV=view.findViewById(R.id.cctvValue);
        txtwater=view.findViewById(R.id.waterValue);
        txtsecurity=view.findViewById(R.id.securityValue);
        txtwifi=view.findViewById(R.id.wifitValue);
        txtcaretaker=view.findViewById(R.id.caretakerValue);
        txtgas=view.findViewById(R.id.gasValue);
        txtelevater=view.findViewById(R.id.liftValue);
        txtgarageF=view.findViewById(R.id.garageFValue);

        txtdetail=view.findViewById(R.id.housedetailValue);


        texhouseName.setText(AdsDetailActivity.addspostModel.houseName);
        txt_balcony.setText(AdsDetailActivity.addspostModel.spinner_balcony+"\tBalcony");
        txt_bathrooms.setText(AdsDetailActivity.addspostModel.spinner_bathrooms+"\tBathrooms");
        txt_floors.setText(AdsDetailActivity.addspostModel.spinner_floor);
        txt_rooms.setText(AdsDetailActivity.addspostModel.spinner_rooms+"\tLiving Rooms");

      //  String str_drawing="";
    //    String str_dining="";
     //   String str_draw_dinn="";
     //   if (str_drawing.equals(AdsDetailActivity.addspostModel.str_drawing)) {
       //     txtDrawing.setVisibility(View.GONE);
     //  }else {
            txtDrawing.setText(AdsDetailActivity.addspostModel.str_drawing);

       // }
        ////

      //  if (str_dining.equals(AdsDetailActivity.addspostModel.str_dinning)) {
        //    txtdining.setVisibility(View.GONE);
       // }else {
            txtdining.setText(AdsDetailActivity.addspostModel.str_dinning);

        //}
//        if (str_draw_dinn.equals(AdsDetailActivity.addspostModel.str_draw_dinn)) {
//            txtDra_din.setVisibility(View.GONE);
//        }else {
            txtDra_din.setText(AdsDetailActivity.addspostModel.str_draw_dinn);

       // }


        txtkitchen.setText(AdsDetailActivity.addspostModel.str_kitchen);

        txtAddress.setText(AdsDetailActivity.addspostModel.address);
        txtrentValue.setText(AdsDetailActivity.addspostModel.edtr_Rent+"."+AdsDetailActivity.addspostModel.str_negotiablefixed);
        txtavailbleMonthValue.setText(AdsDetailActivity.addspostModel.spinner_month);
        txtcontactValue.setText(AdsDetailActivity.addspostModel.edtr_contactNo);

        txtwidthValue.setText(AdsDetailActivity.addspostModel.str_Sq_Feet+"\t sq.feet");

        txtFamily.setText(AdsDetailActivity.addspostModel.checkBoxd_family);
        txtbachelor.setText(AdsDetailActivity.addspostModel.checkBoxd_bachelor);
        txtOffice.setText(AdsDetailActivity.addspostModel.checkBoxd_office);
        txtGodwon.setText(AdsDetailActivity.addspostModel.checkBoxd_goddown);
        txtGaragreForRent.setText(AdsDetailActivity.addspostModel.checkBoxd_garage);
        txtstall.setText(AdsDetailActivity.addspostModel.str_checkBox_stall11);
        txtSublet.setText(AdsDetailActivity.addspostModel.str_checkBox_sublet11);

        txtCCTV.setText(AdsDetailActivity.addspostModel.cctv);
        txtwater.setText(AdsDetailActivity.addspostModel.water);
        txtsecurity.setText(AdsDetailActivity.addspostModel.securityGuard);
        txtwifi.setText(AdsDetailActivity.addspostModel.wifi);
        txtcaretaker.setText(AdsDetailActivity.addspostModel.caretaker);
        txtgas.setText(AdsDetailActivity.addspostModel.gas);
        txtelevater.setText(AdsDetailActivity.addspostModel.str_lift);
        txtgarageF.setText(AdsDetailActivity.addspostModel.str_fagarge);

        alarmtxt.setText(AdsDetailActivity.addspostModel.alarm);
        areatxt.setText(AdsDetailActivity.addspostModel.area);
        citytxt.setText(AdsDetailActivity.addspostModel.spinner_city);
      commercialtxt.setText(AdsDetailActivity.addspostModel.commercial);
        propertyTypetxt.setText(AdsDetailActivity.addspostModel.proType);
        yardTypetxt.setText(AdsDetailActivity.addspostModel.yardType);

        txtdetail.setText(AdsDetailActivity.addspostModel.homeDetail);

        return view;

    }
}