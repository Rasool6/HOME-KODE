package com.variable.home_kode.Users.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.variable.home_kode.R;

public class AddpostActivity3 extends AppCompatActivity {
 EditText edtr_Rent,edtr_Sq_Feet,edtr_contactNo;
 CheckBox negotiable,fixed;
 String str_negotiablefixed;
 Spinner spinner_month;
  private View parent_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost3);

        parent_view = findViewById(android.R.id.content);

        edtr_Rent=findViewById(R.id.editRent);
        negotiable=findViewById(R.id.negogtiableId);
        fixed=findViewById(R.id.fixedId);
        edtr_Sq_Feet=findViewById(R.id.sq_feet);
        edtr_contactNo=findViewById(R.id.editphone);
        spinner_month=findViewById(R.id.month);


        str_negotiablefixed="Negotiable";
        negotiable.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_negotiablefixed="Negotiable";
                    fixed.setChecked(false);
                }
            }
        });
        fixed.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    str_negotiablefixed="Fixed";
                    negotiable.setChecked(false);
                }
            }
        });
    }

    public void next3(View view) {

        String str_edtr_Rent=edtr_Rent.getText().toString().trim();
        String str_edtr_Sq_Feet=edtr_Sq_Feet.getText().toString().trim();
        String str_edtr_contactNo=edtr_contactNo.getText().toString().trim();
        if (TextUtils.isEmpty(str_edtr_Rent)) {
            edtr_Rent.setError("Enter House Rent");
            Snackbar.make(parent_view, "Enter House Rent", Snackbar.LENGTH_SHORT).show();
            return;
            // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }
        else
        if (TextUtils.isEmpty(str_edtr_Sq_Feet)) {
            edtr_Sq_Feet.setError("Enter Sq/Feet");
            Snackbar.make(parent_view, "Enter Feet", Snackbar.LENGTH_SHORT).show();
            return;
            // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        }

        else if (TextUtils.isEmpty(str_edtr_contactNo)) {
            edtr_contactNo.setError("Enter contact No");
            Snackbar.make(parent_view, "Enter contact No", Snackbar.LENGTH_SHORT).show();
            return;
            // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
        } else if (spinner_month.getSelectedItem().toString().equals("Available Month")){
            Toast.makeText(this, "Select Available Month.!", Toast.LENGTH_SHORT).show();
            return;
        }
        else
         {
            startActivity(new Intent(AddpostActivity3.this, AddpostActivity4.class)
            .putExtra("str_edtr_Rent3",str_edtr_Rent)
            .putExtra("str_negotiablefixed3",str_negotiablefixed)
            .putExtra("str_edtr_Sq_Feet3",str_edtr_Sq_Feet)
            .putExtra("str_edtr_contactNo3",str_edtr_contactNo)
            .putExtra("spinner_month3",spinner_month.getSelectedItem().toString())
                 .putExtra("latitude3", getIntent().getStringExtra("latitude2"))
                 .putExtra("longitud3", getIntent().getStringExtra("longitud2"))
                 .putExtra("str_checkBoxd_drawing_dinning3", getIntent().getStringExtra("str_checkBoxd_drawing_dinning2"))
                 .putExtra("str_dinning3", getIntent().getStringExtra("str_dinning2"))
                 .putExtra("str_drawing3", getIntent().getStringExtra("str_drawing2"))
                 .putExtra("str_kitchen3", getIntent().getStringExtra("str_kitchen2"))
                 .putExtra("houseName3", getIntent().getStringExtra("houseName2"))
                 .putExtra("spinner_rooms3", getIntent().getStringExtra("spinner_rooms2"))
                 .putExtra("spinner_bathrooms3", getIntent().getStringExtra("spinner_bathrooms2"))
                 .putExtra("spinner_balcony3", getIntent().getStringExtra("spinner_balcony2"))
                 .putExtra("spinner_floor3", getIntent().getStringExtra("spinner_floor2"))
            );
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddpostActivity3.this,AddpostActivity2.class));
        finish();
    }

    public void back3(View view) {
        startActivity(new Intent(AddpostActivity3.this,AddpostActivity2.class));
        finish();
    }
}