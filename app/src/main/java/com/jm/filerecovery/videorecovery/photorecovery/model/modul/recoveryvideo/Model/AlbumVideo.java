package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryvideo.Model;

import java.util.ArrayList;

public class AlbumVideo {
    String str_folder;
    ArrayList<VideoEntity> listPhoto;
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

    public ArrayList<VideoEntity> getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(ArrayList<VideoEntity> mList) {
        this.listPhoto = mList;
    }
}
