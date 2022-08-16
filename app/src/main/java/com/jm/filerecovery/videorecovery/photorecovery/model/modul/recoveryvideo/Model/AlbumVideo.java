package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model;

import java.util.ArrayList;

public class AlbumVideo {
    String str_folder;
    ArrayList<VideoModel> listPhoto;
    long lastModified;

    public long getLastModified(){
        return  lastModified;
    }
    public void setLastModified(long mLastModified){
        this.lastModified = mLastModified;
    }
    public String getStr_folder() {
        return str_folder;
    }

    public void setStr_folder(String str_folder) {
        this.str_folder = str_folder;
    }

    public ArrayList<VideoModel> getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(ArrayList<VideoModel> mList) {
        this.listPhoto = mList;
    }
}
