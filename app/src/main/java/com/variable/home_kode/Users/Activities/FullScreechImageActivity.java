package com.variable.home_kode.Users.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.variable.home_kode.R;
import com.zolad.zoominimageview.ZoomInImageView;
import com.zolad.zoominimageview.ZoomInImageViewAttacher;

public class FullScreechImageActivity extends AppCompatActivity {

    ZoomInImageView imageView;

    ProgressBar progressBar;
    ZoomInImageViewAttacher mIvAttacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screech_image);
        //     statusBar removal
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mIvAttacter  = new ZoomInImageViewAttacher();
        imageView=findViewById(R.id.imageFull);
        progressBar=findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        String token=getIntent().getStringExtra("img");
     //   imageView.setImage(ImageSource.asset(token));
      //  imageView.setImageResource(Integer.parseInt(token));
        Glide.with(this).load(token.toString()).into(imageView);
       // imageView.setImageURI(Uri.parse(token));
        mIvAttacter.attachImageView(imageView);


        progressBar.setVisibility(View.GONE);



    }
}