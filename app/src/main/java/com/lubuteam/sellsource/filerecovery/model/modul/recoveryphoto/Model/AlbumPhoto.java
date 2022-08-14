package com.lubuteam.sellsource.filerecovery.model.modul.recoveryphoto.Model;

import java.util.ArrayList;

public class AlbumPhoto {
    String str_folder;
    ArrayList<PhotoModel> listPhoto;
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

    public ArrayList<PhotoModel> getListPhoto() {
        return listPhoto;
    }

    public void setListPhoto(ArrayList<PhotoModel> mList) {
        this.listPhoto = mList;
    }
}
