package com.variable.home_kode.Users.Model;

public class VideoModel {
    String videoName;
    String vieoUrl;

    public VideoModel() {
    }

    public VideoModel(String videoName, String vieoUrl) {
        if (videoName.trim().equals("")){
            videoName="No name Available";
        }
        this.videoName = videoName;
        this.vieoUrl = vieoUrl;
    }

    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getVieoUrl() {
        return vieoUrl;
    }

    public void setVieoUrl(String vieoUrl) {
        this.vieoUrl = vieoUrl;
    }
}
