package com.variable.home_kode.Users.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.snackbar.Snackbar;
import com.variable.home_kode.R;

public class AddpostActivity1 extends AppCompatActivity {

    EditText edtName;
    Spinner spinner_rooms,spinner_bathrooms,spinner_balcony,spinner_floor;
    private View parent_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addpost1);
        parent_view = findViewById(android.R.id.content);

        edtName=findViewById(R.id.editTextTextPersonName);
        spinner_rooms=findViewById(R.id.rooms);
        spinner_bathrooms=findViewById(R.id.bathrooms);
        spinner_balcony=findViewById(R.id.balcony);
        spinner_floor=findViewById(R.id.floor);
    }

    public void next1(View view) {
        String str_name=edtName.getText().toString().trim();
        if (TextUtils.isEmpty(str_name))  {
            edtName.setError("Enter House Name");
            Snackbar.make(parent_view, "Enter House Name", Snackbar.LENGTH_SHORT).show();
            return;
            // Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();

        }else if (spinner_rooms.getSelectedItem().toString().equals("No of Living Rooms")) {
            Snackbar.make(parent_view, "please Choose No of Living Rooms", Snackbar.LENGTH_SHORT).show();
            return;
        } else

        if (spinner_bathrooms.getSelectedItem().toString().equals("No of Bathroom")) {
            Snackbar.make(parent_view, "please Choose No of Bathroom", Snackbar.LENGTH_SHORT).show();
            return;
        } else
        if (spinner_balcony.getSelectedItem().toString().equals("No of Balcony")) {
            Snackbar.make(parent_view, "please Choose No of Balcony", Snackbar.LENGTH_SHORT).show();
            return;
        } else
            if (spinner_floor.getSelectedItem().toString().equals("Select the floor")) {
            Snackbar.make(parent_view, "please Choose No of Floor", Snackbar.LENGTH_SHORT).show();
            return;
        } else {

             startActivity(new Intent(AddpostActivity1.this,AddpostActivity2.class)
             .putExtra("str_name1",str_name)
             .putExtra("spinner_rooms1",spinner_rooms.getSelectedItem().toString())
             .putExtra("spinner_bathrooms1",spinner_bathrooms.getSelectedItem().toString())
             .putExtra("spinner_balcony1",spinner_balcony.getSelectedItem().toString())
             .putExtra("spinner_floor1",spinner_floor.getSelectedItem().toString())

            );

        }



    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(AddpostActivity1.this,User_DashboardpActivity.class));
        finish();
    }

    public void back1(View view) {
        startActivity(new Intent(AddpostActivity1.this,User_DashboardpActivity.class));
        finish();
    }
}