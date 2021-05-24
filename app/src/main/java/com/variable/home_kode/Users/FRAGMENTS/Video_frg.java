package com.variable.home_kode.Users.FRAGMENTS;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.variable.home_kode.R;
import com.variable.home_kode.Users.Activities.AdsDetailActivity;
import com.variable.home_kode.Users.Adapters.Home_adapeter;
import com.variable.home_kode.Users.Model.AddspostModel;

import java.util.ArrayList;
import java.util.List;


public class Video_frg extends Fragment {

    VideoView videoView;
    private MediaController mediaController;
    ProgressDialog progressDialog;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_video_frg, container, false);
        progressDialog=new ProgressDialog(getContext());
        videoView=view.findViewById(R.id.videoView);
        mediaController = new MediaController(getContext());
        videoView.setMediaController(mediaController);

        try {
            videoView.setVideoPath(AdsDetailActivity.addspostModel.videoUri);
            videoView.requestFocus();
            videoView.start();
        }catch (Exception e)
        {
         //   Toast.makeText(getContext(), "e.getMessage()", Toast.LENGTH_SHORT).show();
        }

        return view;
    }


}