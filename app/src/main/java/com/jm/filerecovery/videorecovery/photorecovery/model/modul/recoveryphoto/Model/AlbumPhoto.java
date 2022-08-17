package com.jm.filerecovery.videorecovery.photorecovery.model.modul.recoveryphoto.Model;

import java.util.ArrayList;

public class AlbumPhoto {
    String str_folder;
    ArrayList<PhotoEntity> listPhoto;
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

    public ArrayList<PhotoEntity> getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(ArrayList<PhotoEntity> mList) {
        this.listPhoto = mList;
    }
}
